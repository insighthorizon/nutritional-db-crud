package springProj.nutrDB.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springProj.nutrDB.models.dto.FoodDTO;
import springProj.nutrDB.models.dto.mappers.FoodMapper;
import springProj.nutrDB.models.exceptions.FoodNotFoundException;
import springProj.nutrDB.models.services.FoodService;


@Controller
@RequestMapping("/foods")
public class FoodsController {
    /**
     * max size of one page in food listing
     */

    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodMapper foodMapper;


    @GetMapping({"", "/{detailId}"})
    public String renderIndex(
            @RequestParam(value = "sort", required = false) String sortAttribute,
            @RequestParam(name = "search", defaultValue = "") String searchedFoodName,
            @RequestParam(name = "page", defaultValue = "1") int currentPageNumber,
            Model model) {

        final int PAGE_SIZE = 4;

        Page foodsPage = foodService.getPage(currentPageNumber, PAGE_SIZE, searchedFoodName, sortAttribute);

        model.addAttribute("foods", foodsPage.getContent());
        model.addAttribute("currentPageNumber", foodsPage.getNumber() + 1);
        model.addAttribute("totalPages", foodsPage.getTotalPages());
        model.addAttribute("searchedName", searchedFoodName);

        return "/pages/foods/index";
    }


    @GetMapping("create")
    public String renderCreateForm(@ModelAttribute FoodDTO food) {
        return "/pages/foods/create";
    }

    @PostMapping("create")
    public String create(
            @Valid @ModelAttribute FoodDTO food,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        // provedeni validace probehlo uz pred vstupem do teto metody (@Valid ... )
        // negativni vysledek validace zpracujeme hned:
        if (result.hasErrors())
            return renderCreateForm(food);
        // pripadne errory uz budou obsazeny na zaklade predani DTO, protoze DTO ma specifikovane validacni zpravy

        foodService.create(food);

        redirectAttributes.addFlashAttribute("success", "Potravina vytvořena.");

        return "redirect:/foods";
    }

    @GetMapping("edit/{foodId}")
    public String renderEditForm(
            @PathVariable(value = "foodId") long foodId,
            @ModelAttribute FoodDTO food
    ) {
        FoodDTO fetchedFood = foodService.getById(foodId);
        foodMapper.updateFoodDTO(fetchedFood, food);

        return "/pages/foods/edit";
    }

    @PutMapping("edit/{foodId}")
    public String editFood(
            @PathVariable("foodId") long foodId,
            @Valid @ModelAttribute FoodDTO food,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors())
            return renderEditForm(foodId, food);

        foodService.edit(food);

        redirectAttributes.addFlashAttribute("success", "Potravina upravena.");

        return "redirect:/foods";
    }

    @GetMapping("detail/{foodId}")
    public String renderDetail(@PathVariable("foodId") long foodId,
                               @ModelAttribute FoodDTO food) {
        FoodDTO fetchedFood = foodService.getById(foodId);
        foodMapper.updateFoodDTO(fetchedFood, food);

        return "/pages/foods/detail";
    }

    @DeleteMapping("delete/{foodId}")
    public String deleteFood(@PathVariable("foodId") long foodId,
                             RedirectAttributes redirectAttributes) {
        foodService.remove(foodId);
        redirectAttributes.addFlashAttribute("success", "Potravina smazána.");

        return "redirect:/foods";
    }

    @ExceptionHandler({FoodNotFoundException.class})
    public String handleFoodNotFoundException (RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Potravina nenalezena.");
        return "redirect:/foods";
    }

}
