package bt06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " - " + price;
    }
}

public class Main {
    public static void main(String[] args) {

        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", 1500));
        products.add(new Product("Mouse", 25));
        products.add(new Product("Keyboard", 75));
        products.add(new Product("Monitor", 300));

        Collections.sort(products, new Comparator<Product>() {

            int compareCount = 0;

            @Override
            public int compare(Product p1, Product p2) {
                compareCount++;
                return Double.compare(p1.getPrice(), p2.getPrice());
            }
        });

        System.out.println("Sap xep theo gia tang dan (Anonymous Class):");
        for (Product p : products) {
            System.out.println(p);
        }

        Collections.sort(products, (p1, p2) ->
                p1.getName().compareTo(p2.getName())
        );

        System.out.println("\nSap xep theo ten A-Z (Lambda):");
        for (Product p : products) {
            System.out.println(p);
        }
    }
}
