package model;

public class Food extends MenuItem {
    private String category;

    public Food(String id, String name, double price, String category) {
        super(id, name, price);
        this.category = category;
    }

    @Override
    public double calculatePrice() {
        return getPrice();
    }
}
