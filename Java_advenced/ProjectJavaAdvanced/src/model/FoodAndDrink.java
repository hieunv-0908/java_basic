package model;

public class FoodAndDrink {
    private int foodAndDrinkId;
    private String name;
    private String description;
    private double price;
    private int stock;

    public FoodAndDrink(int foodAndDrinkId, String name, String description, double price, int stock) {
        this.foodAndDrinkId = foodAndDrinkId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public FoodAndDrink() {
    }

    public int getFoodAndDrinkId() {
        return foodAndDrinkId;
    }

    public void setFoodAndDrinkId(int foodAndDrinkId) {
        this.foodAndDrinkId = foodAndDrinkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
