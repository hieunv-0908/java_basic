package service;

import model.MenuItem;
import model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {

    private List<Order> orders;

    public ReportService(List<Order> orders) {
        this.orders = orders;
    }

    public double calculateTotalRevenue() {

        return orders.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();

    }

    public Map<MenuItem, Integer> getBestSellingItems() {

        Map<MenuItem, Integer> result = new HashMap<>();

        for(Order order : orders) {

            for(Map.Entry<MenuItem, Integer> entry : order.getItems().entrySet()) {

                result.put(
                        entry.getKey(),
                        result.getOrDefault(entry.getKey(), 0) + entry.getValue()
                );

            }

        }

        return result;
    }
}