package springProj.nutrDB.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Specifies how to handle http requests at home URL address
 */
@Controller
public class HomeController {
    /**
     * Handle GET request at "/" URL by rendering index.html
     * @return reference to the index.tml template
     */
    @GetMapping("/")
    public String renderIndex() {
        return "pages/home/index";
    }

}
