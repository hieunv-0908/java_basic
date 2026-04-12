package re.session05.bai3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import re.session05.bai2.model.Dish;
import re.session05.bai3.service.AdminDishService;

import java.util.Optional;

@Controller
public class AdminDishController {

    @Autowired
    private AdminDishService adminDishService;

    @GetMapping("/bai3/edit/{id}")
    public String editDish(@PathVariable int id, Model model) {

        Optional<Dish> dishOpt = adminDishService.findById(id);

        if (dishOpt.isEmpty()) {
            model.addAttribute("error", "Không tìm thấy món ăn yêu cầu!");
            return "redirect:/bai2/dishes";
        }

        model.addAttribute("dish", dishOpt.get());
        return "edit-dish";
    }
}
