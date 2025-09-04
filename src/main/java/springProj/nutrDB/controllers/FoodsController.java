package springProj.nutrDB.controllers;

import jakarta.validation.Valid;
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
     * Constructor used by Spring IoC container to instantiate this class and
     * to inject dependencies (via constructor parameters).
     * This class has only one constructor, so no @Autowired annotation needed.
     * @param foodService instance provided by dependency injection
     * @param foodMapper instance provided by dependency injection
     */
    public FoodsController(FoodService foodService, FoodMapper foodMapper) {
        this.foodService = foodService;
        this.foodMapper = foodMapper;
    }

    /**
     * The controller interacts with the model (persistent data) through service (MVC);
     */
    private final FoodService foodService;

    /**
     * For converting between FoodEntity and FoodDTO and updating one based on the other;
     */
    private final FoodMapper foodMapper;

    /**
     * Handles GET requests at "/foods" URL by attempting to load a requested page of foods from the database
     * and subsequently rendering those in /foods/index.html template;
     * Informs the client if no foods were found.
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

        Page<FoodDTO> foodsPage = foodService.getPage(currentPageNumber, PAGE_SIZE, searchedFoodName, sortAttribute);

        // making data avaiable to the template (view)
        model.addAttribute("foods", foodsPage.getContent());
        model.addAttribute("currentPageNumber", foodsPage.getNumber() + 1);
        model.addAttribute("totalPages", foodsPage.getTotalPages());
        model.addAttribute("searchedName", searchedFoodName);
        model.addAttribute("sortAttribute", sortAttribute);

        return "/pages/foods/index";
    }

    /**
     * Handles GET request at "/foods/create" URL by rendering the /foods/create.html tempalte;
     * Action available only to the authenticated users with role "ROLE_USER" or "ROLE_ADMIN" (@Secured).
     * @param foodDTO Either empty or data previously entered by the user, now to be rendered in the template;
     *                It will be non-empty only if validation in previous create attempt failed;
     *                DTO is data not yet saved in database or a copy of actual database entry;
     *                (@ModelAttribute makes it avaiable in the view template.)
     * @return Reference to the /foods/create.html tempalte.
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("create")
    public String renderCreateForm(@ModelAttribute FoodDTO foodDTO) {
        return "/pages/foods/create";
    }

    /**
     * Handles POST request at "/foods/create" URL by creating a new food entry
     * if the provided food data was validated without errors and informs the client about the result;
     * (The validation is performed before this method is even reached. But it is specified here through @Valid that it has to happen.)
     * Action available only to authenticated users with role "ROLE_USER" or "ROLE_ADMIN" (@Secured).
     * @param foodDTO ModelAttribute exposes the food data to the rendered template (view). New food data provided by the user;
     *                DTO is data not yet saved in database or a copy of actual database entry;
     *                (@ModelAttribute makes it avaiable in the view template, so that user modify it.)
     * @param result Result of validation.
     * @param redirectAttributes Will contain result message sent to the template this method may redirect to.
     * @return Redirection to "/foods" URL or reference to template to render.
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("create")
    public String create(
            @Valid @ModelAttribute FoodDTO foodDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) // check for validation errors
            return renderCreateForm(foodDTO); // DTO will automatically contain the error messages (bean validation API)

        foodService.create(foodDTO);

        // A new food entry was created, produce an appropriate response to the client.
        redirectAttributes.addFlashAttribute("success", "Potravina vytvořena.");
        return "redirect:/foods";
    }

    /**
     * Handles GET request at "/foods/edit/{foodId}" URL address by rendering the
     * /foods/edit.thml template (edit form);
     * The URL contains the foodID as a path variable (@PathVariable);
     * (@ModelAttribute exposes the food data to the rendered template (view).)
     * Action available only to authenticated users with role "ROLE_ADMIN" (@Secured).
     * @param foodId ID of the edited food.
     * @param foodDTO The food data being edited.
     *                DTO is data not yet saved in database or a copy of actual database entry.
     * @return Reference to the /foods/edit.html template.
     * @throws FoodNotFoundException handled by {@link #handleFoodNotFoundException(RedirectAttributes)}
     */
    @Secured("ROLE_ADMIN") // editing will be available only for admin accounts
    @GetMapping("edit/{foodId}")
    public String renderEditForm(
            @PathVariable(value = "foodId") long foodId,
            @ModelAttribute FoodDTO foodDTO
    ) throws FoodNotFoundException {
        FoodDTO fetchedFood = foodService.getById(foodId); // can throw FoodNotFoundException
        foodMapper.updateFoodDTO(fetchedFood, foodDTO);

        return "/pages/foods/edit";
    }

    /**
     * Handles PUT request at "/foods/edit{foodId}" URL address by editing the food entry in the database
     * based on the foodDTO if it was validated without errors and informs the client about the result.
     * (The validation is performed before this method is even reached. But it is specified here through @Valid that it has to happen.)
     * The URL contains the id of the food as a path variable (@PathVariable).
     * (@ModelAttribute exposes the food data to the rendered template (view).)
     * Action available only to authenticated users with role "ROLE_ADMIN" (@Secured).
     * @param foodId ID of the edited food.
     * @param foodDTO The food data being edited.
     * @param result Result of validation.
     * @param redirectAttributes Will contain result message sent to the template this method may redirect to.
     * @return Reference to /foods/edit.html template or redirection to "/foods" URL.
     * @throws FoodNotFoundException handled by {@link #handleFoodNotFoundException(RedirectAttributes)}
     */
    @Secured("ROLE_ADMIN") // editing will be available only for admin accounts
    @PutMapping("edit/{foodId}")
    public String edit(
            @PathVariable("foodId") long foodId,
            @Valid @ModelAttribute FoodDTO foodDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) throws FoodNotFoundException{
        if (result.hasErrors()) // check for validation errors
            return renderEditForm(foodId, foodDTO); // DTO will automatically contain the error messages (bean validation API)

        foodService.edit(foodDTO); // can throw FoodNotFoundException

        // A food entry was edited, produce an appropriate response to the client.
        redirectAttributes.addFlashAttribute("success", "Potravina upravena.");
        return "redirect:/foods";
    }

    /**
     * Handles GET request at the "/foods/detail/{foodId}" URL address by renderin gthe /foods/detail.html template;
     * The URL contains the id of the food as a path variable (@PathVariable).
     * (@ModelAttribute exposes the food data to the rendered template (view).)
     * @param foodId ID of the food selected for deletion.
     * @param foodDTO Data of the selected food.
     * @return Reference to the /foods/detail.html template.
     * @throws FoodNotFoundException handled by {@link #handleFoodNotFoundException(RedirectAttributes)}
     */
    @GetMapping("detail/{foodId}")
    public String renderDetail(@PathVariable("foodId") long foodId,
                               @ModelAttribute FoodDTO foodDTO) throws FoodNotFoundException {
        FoodDTO fetchedFood = foodService.getById(foodId); // can throw FoodNotFoundException
        foodMapper.updateFoodDTO(fetchedFood, foodDTO);

        return "/pages/foods/detail";
    }

    /**
     * Handles DELETE request at the "/foods/detete/{foodId}" URL address by attempting to delete
     * the food with given id from the database;
     * The URL contains the id of the food as a path variable (@PathVariable).
     * @param foodId ID of the food being deleted
     * @param redirectAttributes Will contain the result message sent to template through redirection.
     * @return Redirection to "/foods" URL.
     * @throws FoodNotFoundException handled by {@link #handleFoodNotFoundException(RedirectAttributes)}
     */
    @Secured("ROLE_ADMIN") // deleting available only to admin accounts
    @DeleteMapping("delete/{foodId}")
    public String deleteFood(@PathVariable("foodId") long foodId,
                             RedirectAttributes redirectAttributes) throws FoodNotFoundException {
        foodService.remove(foodId); // can throw FoodNotFoundException

        // The food entry was succesfuly deleted, report that to the client.
        redirectAttributes.addFlashAttribute("success", "Potravina smazána.");
        return "redirect:/foods";
    }

    /**
     * Handles the exception when database fails to find the food entry being requested by
     * rendering the error response in the "/foods" page. This exception may occur when the entry which
     * one client attempts to access is already deleted by another client.
     * @param redirectAttributes Will contain the error message sent to the client.
     * @return Redirection to the "/foods" URL.
     */
    @ExceptionHandler({FoodNotFoundException.class})
    public String handleFoodNotFoundException (RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Potravina nenalezena.");
        return "redirect:/foods";
    }

}
