package model;

public class Dessert extends MenuItem {

    private int sweetnessLevel;

    public Dessert(String id, String name, double price, int sweetnessLevel) {
        super(id, name, price);
        this.sweetnessLevel = sweetnessLevel;
    }

    @Override
    public double calculatePrice() {
        return getPrice();
    }
}