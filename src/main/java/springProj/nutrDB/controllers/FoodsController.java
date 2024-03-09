package springProj.nutrDB.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/foods")
public class FoodsController {
    @GetMapping
    public String renderIndex(
            @RequestParam(name = "page", defaultValue = "1") int pageNumber,
            Model model
    ) {
        if (pageNumber < 1) pageNumber = 1;

        return "/pages/foods/index";
    }

    @GetMapping("create")
    public String renderCreateForm() {
        return "/pages/foods/create";
    }

    @GetMapping("detail/{foodId}")
    public String renderDetail(@PathVariable("foodId") long foodId) {
        return "/pages/foods/detail";
    }

    @GetMapping("edit/{foodId}")
    public String renderEditForm(@PathVariable("foodId") long foodId) {
        return "/pages/foods/edit";
    }

    @GetMapping("search")
    public String renderSearchResults() {
        return "/pages/foods/search";
    }

}
