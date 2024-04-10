package springProj.nutrDB.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springProj.nutrDB.models.dto.UserDTO;
import springProj.nutrDB.models.exceptions.DuplicateEmailException;
import springProj.nutrDB.models.exceptions.PasswordsNotEqualException;
import springProj.nutrDB.models.services.UserService;

/**
 * Specifies how to handle http requests at "/account/**" URL addresses.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    /**
     * The controller interacts with the model (MVC) through service;
     * Autowired (instance provided by dependency injection).
     */
    @Autowired
    private UserService userService;

    /**
     * Handles GET request at "/account/login" URL by rendering login.html
     * @return reference to the login.html template
     */
    @GetMapping("login")
    public String renderLogin() {
        return "/pages/account/login";
    }

    /**
     * Handles GET request "/account/register" URL by rendering register.html with userDTO data.
     * @param userDTO Either empty or data previously provided by the user, now to be redered in the template;
     *                It will be non-empty only after previous failed attempt at validation or account creation;
     *                (@ModelAttribute makes it avaiable in the view template.)
     * @return reference to the register.html template
     */
    @GetMapping("register")
    public String renderRegister(@ModelAttribute UserDTO userDTO) {
        return"/pages/account/register";
    }

    /**
     * Handles POST request at "/account/register" URL by attempting to create a new account
     * if the user data has been validated without erros and informs the client about the resutl.
     * (The validation is performed before this method is even reached. But it is specified here
     * through @Valid that it has to happen.)
     * @param userDTO Validated user registration data received from the registration form (view);
     *                DTO is data not saved in database yet or a copy of actual database entry;
     *                (@ModelAttribute makes it avaiable in the view template.)
     * @param result Result of validation
     * @param redirectAttributes Will contain the result message sent to a template this method may redirect to.
     * @return A redirection to login page or reference to template to render.
     */
    @PostMapping("register")
    public String register(
            @Valid @ModelAttribute UserDTO userDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) // check for validation errors
            return renderRegister(userDTO); // DTO will automatically contain the error messages (bean validation API)

        try { // attempt to add a new user into the database
            userService.create(userDTO, false);
        } catch (DuplicateEmailException e) {
            result.rejectValue("email", "error", "Email je již zaregistrován.");
            return "/pages/account/register";
        } catch (PasswordsNotEqualException e) {
            result.rejectValue("password", "error", "Hesla se neshodují");
            result.rejectValue("passwordConfirmation", "error", "Hesla se neshodují");
            return "/pages/account/register";
        }

        // The account was succesfully created. Produce an appropriate response to the client.
        redirectAttributes.addFlashAttribute("success", "Uživatel zaregistrován.");
        return "redirect:/account/login";
    }

}
