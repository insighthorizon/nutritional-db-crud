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

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String renderLogin() {
        return "/pages/account/login";
    }

    @GetMapping("register")
    public String renderRegister(@ModelAttribute UserDTO userDTO) {
        return"/pages/account/register";
    }

    @PostMapping("register")
    public String register(
            @Valid @ModelAttribute UserDTO userDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) // handling validation errors
            return renderRegister(userDTO);

        // attempt to add a new user into the database
        try {
            userService.create(userDTO, false);
        } catch (DuplicateEmailException e) {
            result.rejectValue("email", "error", "Email je již zaregistrován.");
            return "/pages/account/register";
        } catch (PasswordsNotEqualException e) {
            result.rejectValue("password", "error", "Hesla se neshodují");
            result.rejectValue("passwordConfirmation", "error", "Hesla se neshodují");
            return "/pages/account/register";
        }

        // if everything went through without raising an exception
        redirectAttributes.addFlashAttribute("success", "Uživatel zaregistrován.");
        return "redirect:/account/login";
    }

}
