package model;

public class OrderFoodItem {
    private int id;
    private int orderId;
    private int foodAndDrinkId;
    private int quantity;
    private double price;

    public OrderFoodItem(int id, int orderId, int foodAndDrinkId, int quantity, double price) {
        this.id = id;
        this.orderId = orderId;
        this.foodAndDrinkId = foodAndDrinkId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderFoodItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getFoodAndDrinkId() {
        return foodAndDrinkId;
    }

    public void setFoodAndDrinkId(int foodAndDrinkId) {
        this.foodAndDrinkId = foodAndDrinkId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
