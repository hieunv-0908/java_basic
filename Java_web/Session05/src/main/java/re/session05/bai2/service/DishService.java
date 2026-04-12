package re.session05.bai2.service;

import re.session05.bai2.model.Dish;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishService {

    public List<Dish> getAllDishes() {
        List<Dish> list = new ArrayList<>();

        list.add(new Dish(1, "Phở bò", 50000, true));
        list.add(new Dish(2, "Bún chả", 45000, false));
        list.add(new Dish(3, "Cơm rang", 40000, true));

        return list;
    }
}