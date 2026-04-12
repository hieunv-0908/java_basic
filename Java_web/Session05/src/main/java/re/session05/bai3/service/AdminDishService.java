package re.session05.bai3.service;
import org.springframework.stereotype.Service;
import re.session05.bai2.model.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminDishService {

    private final List<Dish> dishes = new ArrayList<>();

    public AdminDishService() {
        dishes.add(new Dish(1, "Phở bò", 50000, true));
        dishes.add(new Dish(2, "Bún chả", 45000, false));
        dishes.add(new Dish(3, "Cơm rang", 40000, true));
    }

    public Optional<Dish> findById(int id) {
        return dishes.stream()
                .filter(d -> d.getId() == id)
                .findFirst();
    }
}
