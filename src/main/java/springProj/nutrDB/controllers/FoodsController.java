package springProj.nutrDB.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springProj.nutrDB.models.dto.FoodDTO;
import springProj.nutrDB.models.dto.mappers.FoodMapper;
import springProj.nutrDB.models.exceptions.FoodNotFoundException;
import springProj.nutrDB.models.services.FoodService;

/**
 * Specifies how to handle http requests at "/foods/**" URL addresses.
 */
@Controller
@RequestMapping("/foods")
public class FoodsController {

    /**
     * The controller interacts with the model (MVC) through service;
     * Autowired - instance provided by dependency injection.
     */
    @Autowired
    private FoodService foodService;

    /**
     * For converting between FoodEntity and FoodDTO and updating one based on the other;
     * Autowired - instance provided by dependency injection.
     */
    @Autowired
    private FoodMapper foodMapper;

    /**
     * Handles GET requests at "/foods" by attempting to load a requested page of foods from the database
     * and subsequently rendering those in /foods/index.html template.
     * @param sortAttribute Food attribute by which to sort displayed foods;
     *                      Passed as URL parameter (@RequestParam).
     * @param searchedFoodName Substring required in food name of all foods being displayed;
     *                         (Empty string is substring of every string);
     *                         Passed as URL parameter (@RequestParam).
     * @param currentPageNumber Number of the requested page;
     *                          Passed as URL parameter (@RequestParam).
     * @param model Data being exposed to the view. Contains the list of displayed foods and more.
     * @return Reference to the /foods/index.html template.
     */
    @GetMapping
    public String renderIndex(
            @RequestParam(value = "sort", required = false) String sortAttribute,
            @RequestParam(name = "search", defaultValue = "") String searchedFoodName,
            @RequestParam(name = "page", defaultValue = "1") int currentPageNumber,
            Model model) {
// add test data
//        FoodDTO food = new FoodDTO();
//        for (int i = 0; i < 26; i++) {
//            char c = (char)('a' + i);
//            String string = Character.toString('a' + i);
//            string = string + string + string + string;
//            food.setName(string);
//            food.setKcal((short)(10 * i));
//            food.setCarbs(BigDecimal.valueOf(4 * i));
//            food.setProtein(BigDecimal.valueOf(15 * i, 1));
//            food.setFats(BigDecimal.valueOf(5 * i, 1));
//
//            foodService.create(food);
//        }

        final int PAGE_SIZE = 10; // max size of one page for pagination in food entries

        Page foodsPage = foodService.getPage(currentPageNumber, PAGE_SIZE, searchedFoodName, sortAttribute);

        // making data avaiable to the template (view)
        model.addAttribute("foods", foodsPage.getContent());
        model.addAttribute("currentPageNumber", foodsPage.getNumber() + 1);
        model.addAttribute("totalPages", foodsPage.getTotalPages());
        model.addAttribute("searchedName", searchedFoodName);
        model.addAttribute("sortAttribute", sortAttribute);

        return "/pages/foods/index";
    }

    /**
     * Handles GET request at "/foods/create" by rendering the /foods/create.html tempalte;
     * Available only to authenticated users with role "ROLE_USER" or "ROLE_ADMIN" (@Secured).
     * @param foodDTO Either empty or data previously entered by the user, now to be rendered in the template;
     *                It will be non-empty only if validation in previous create attempt failed;
     *                (@ModelAttribute makes it avaiable in the view template.)
     * @return reference to the /foods/create.html tempalte
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("create")
    public String renderCreateForm(@ModelAttribute FoodDTO foodDTO) {
        return "/pages/foods/create";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
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

    @Secured("ROLE_ADMIN") // editing will be available only for admin accounts
    @GetMapping("edit/{foodId}")
    public String renderEditForm(
            @PathVariable(value = "foodId") long foodId,
            @ModelAttribute FoodDTO food
    ) {
        FoodDTO fetchedFood = foodService.getById(foodId);
        foodMapper.updateFoodDTO(fetchedFood, food);

        return "/pages/foods/edit";
    }

    @Secured("ROLE_ADMIN") // editing will be available only for admin accounts
    @PutMapping("edit/{foodId}")
    public String edit(
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

    @Secured("ROLE_ADMIN")
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
