package springProj.nutrDB.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Specifies how to handle http requests at home URL address
 */
@Controller
public class HomeController {
    /**
     * No dependencies
     */
    public HomeController() {
    }

    /**
     * Handle GET request at "/" URL by rendering index.html
     * @return Reference to the index.tml template.
     */
    @GetMapping("/")
    public String renderIndex() {
        return "pages/home/index";
    }

}
