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
    private static final int PAGE_SIZE = 2;

    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodMapper foodMapper;

    @GetMapping
    public String renderIndex(
            @RequestParam(name = "page", defaultValue = "1") int currentPageNumber,
            Model model
    ) {
        if (currentPageNumber < 1) currentPageNumber = 1;

        Page<FoodDTO> foodsPage = foodService.getPage(currentPageNumber, PAGE_SIZE);
        int totalPages = foodsPage.getTotalPages();

        // keep looking even if client requested non-existent page
        while (totalPages != 0 && (currentPageNumber > totalPages) /* foodsPage.isEmpty() */) {
            currentPageNumber = totalPages;
            foodsPage = foodService.getPage(currentPageNumber, PAGE_SIZE);
            totalPages = foodsPage.getTotalPages();
        }

        fillFoodsModelForTemplate(model, foodsPage, currentPageNumber, totalPages);

        return "/pages/foods/index";
    }

    @GetMapping("search")
    public String renderSearch(@RequestParam(name = "foodName", required = false) String foodName,
                               @RequestParam(name = "page", defaultValue = "1") int currentPageNumber,
                               Model model) {
        if (currentPageNumber < 1) currentPageNumber = 1;

        Page<FoodDTO> foodsPage = foodService.getNamesearchPage(currentPageNumber, PAGE_SIZE, foodName);
        int totalPages = foodsPage.getTotalPages();

        // keep looking even if client requested non-existent page
        while (totalPages != 0 && (currentPageNumber > totalPages) /* foodsPage.isEmpty() */) {
            currentPageNumber = totalPages;
            foodsPage = foodService.getNamesearchPage(currentPageNumber, PAGE_SIZE, foodName);
            totalPages = foodsPage.getTotalPages();
        }

        fillFoodsModelForTemplate(model, foodsPage, currentPageNumber, totalPages);

        return "/pages/foods/search";
    }

    private void fillFoodsModelForTemplate(Model model,
                                           Page foodsPage,
                                           int currentPageNumber,
                                           int totalPages) {
        model.addAttribute("foods", foodsPage.getContent());
        model.addAttribute("currentPageNumber", currentPageNumber);
        model.addAttribute("totalPages", totalPages);
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
            @PathVariable("foodId") long foodId,
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
