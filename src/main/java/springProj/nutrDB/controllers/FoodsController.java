package springProj.nutrDB.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springProj.nutrDB.models.dto.FoodDTO;
import springProj.nutrDB.models.dto.mappers.FoodMapper;
import springProj.nutrDB.models.exceptions.FoodNotFoundException;
import springProj.nutrDB.models.exceptions.GramValueException;
import springProj.nutrDB.models.exceptions.KcalMismatchException;
import springProj.nutrDB.models.services.FoodService;

@Controller
@RequestMapping("/foods")
public class FoodsController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodMapper foodMapper;

    @GetMapping
    public String renderIndex(
            @RequestParam(name = "page", defaultValue = "1") int pageNumber,
            Model model
    ) {
        if (pageNumber < 1) pageNumber = 1;

        return "/pages/foods/index";
    }

    private void produceGramValueErrors(BindingResult result) {
        result.rejectValue("protein", "error", "Součet gramů celkem nestmí přesáhnout 100 g.");
        result.rejectValue("carbs", "error", "Součet gramů celkem nestmí přesáhnout 100 g.");
        result.rejectValue("fats", "error", "Součet gramů celkem nestmí přesáhnout 100 g.");
    }

    private void produceKcalMismatchError(BindingResult result) {
        result.rejectValue("kcal", "error", "Kalorická hodnota neodpovídá hodnotám makronutrientů.");
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

        try {
            foodService.create(food);
        } catch (GramValueException e) {
            produceGramValueErrors(result);

            return renderCreateForm(food);
        } catch (KcalMismatchException e) {
            produceKcalMismatchError(result);

            return renderCreateForm(food);
        }

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

        try {
            foodService.edit(food);
        } catch (GramValueException e) {
            produceGramValueErrors(result);

            return renderEditForm(foodId, food);
        } catch (KcalMismatchException e) {
            produceKcalMismatchError(result);

            return renderEditForm(foodId, food);
        }

        redirectAttributes.addFlashAttribute("success", "Potravina upravena.");

        return "redirect:/foods";
    }

    @GetMapping("detail/{foodId}")
    public String renderDetail(@PathVariable("foodId") long foodId) {
        return "/pages/foods/detail";
    }


    @GetMapping("search")
    public String renderSearchResults() {
        return "/pages/foods/search";
    }

    @ExceptionHandler({FoodNotFoundException.class})
    public String handleFoodNotFoundException (RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Potravina nenalezena.");
        return "redirect:/foods";
    }

}
