package re.session05.bai2.controller;

import re.session05.bai2.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/bai2/dishes")
    public String showDishes(Model model) {
        model.addAttribute("dishes", dishService.getAllDishes());
        return "dish-list";
    }
}
