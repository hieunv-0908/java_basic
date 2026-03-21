package model;

import service.InsufficientStockException;

import java.util.HashMap;
import java.util.Map;

public class Order {

    private String id;
    private Map<MenuItem, Integer> items;
    private OrderStatus status;
    private double totalPrice;

    public Order(String id) {
        this.id = id;
        this.items = new HashMap<>();
        this.status = OrderStatus.PENDING;
    }

    public void addItem(MenuItem item, int quantity)
            throws InsufficientStockException {

        if(quantity <= 0){
            throw new InsufficientStockException("Quantity must be greater than 0");
        }

        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public void removeItem(MenuItem item){
        items.remove(item);
    }

    public double calculateTotal(){

        totalPrice = items.entrySet()
                .stream()
                .mapToDouble(e -> e.getKey().calculatePrice() * e.getValue())
                .sum();

        return totalPrice;
    }

    public void applyDiscount(double percent){

        if(percent > 0 && percent <= 100){
            totalPrice = totalPrice - totalPrice * percent / 100;
        }else{
            throw new IllegalArgumentException("Invalid discount percentage.");
        }

    }

    public String getId(){
        return id;
    }

    public Map<MenuItem,Integer> getItems(){
        return items;
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    public OrderStatus getStatus(){
        return status;
    }

    public void setStatus(OrderStatus status){
        this.status = status;
    }
}