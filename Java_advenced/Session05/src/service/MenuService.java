package service;

import model.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MenuService {

    private List<MenuItem> menuList = new ArrayList<>();

    public void addMenuItem(MenuItem item) {
        menuList.add(item);
    }

    public void updateMenuItem(MenuItem item) {

        for(int i = 0; i < menuList.size(); i++) {

            if(menuList.get(i).getId().equals(item.getId())) {
                menuList.set(i, item);
                return;
            }

        }
    }

    public void deleteMenuItem(String id) {

        menuList.removeIf(item -> item.getId().equals(id));

    }

    public List<MenuItem> findByName(String name) {

        return menuList.stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

    }

    public List<MenuItem> findByPrice(double min, double max) {

        return menuList.stream()
                .filter(item -> item.getPrice() >= min && item.getPrice() <= max)
                .collect(Collectors.toList());

    }

    public List<MenuItem> getAllMenuItems() {
        return menuList;
    }
}