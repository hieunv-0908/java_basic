package model;

public class Drink extends MenuItem {
    private String size;

    public Drink(String id, String name, double price, String size) {
        super(id, name, price);
        this.size = size;
    }

    @Override
    public double calculatePrice() {

        if(size.equalsIgnoreCase("L")) {
            return getPrice() + 5;
        }

        if(size.equalsIgnoreCase("M")) {
            return getPrice() + 3;
        }

        return getPrice();
    }
}
