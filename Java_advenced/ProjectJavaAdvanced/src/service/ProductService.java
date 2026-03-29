package service;

import dao.FoodAndDrinkDAO;
import model.FoodAndDrink;

import java.util.List;

public class ProductService {
    List<FoodAndDrink> foodAndDrinks;

    public ProductService() {
        this.foodAndDrinks = FoodAndDrinkDAO.queryDb();
    }

    // Display all food and drinks in table format
    public void displayAllProducts() {
        foodAndDrinks = FoodAndDrinkDAO.queryDb();
        if (foodAndDrinks.isEmpty()) {
            System.out.println("No products available!");
            return;
        }

        // Print table header
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        DANH MỤC ĐỒ ĂN / THỨC UỐNG                             ║");
        System.out.println("╠════╦═══════════════════╦═══════════════════════════╦═════════╦═══════════════╣");
        System.out.printf("║ ID ║      TÊN          ║       MÔ TẢ               ║  GIÁ    ║  TỒN KHO      ║%n");
        System.out.println("╠════╬═══════════════════╬═══════════════════════════╬═════════╬═══════════════╣");

        // Print data rows
        for (FoodAndDrink foodAndDrink : foodAndDrinks) {
            System.out.printf("║ %-2d ║ %-17s ║ %-25s ║ %7.2f ║ %13d ║%n",
                    foodAndDrink.getFoodAndDrinkId(),
                    truncateString(foodAndDrink.getName(), 17),
                    truncateString(foodAndDrink.getDescription(), 25),
                    foodAndDrink.getPrice(),
                    foodAndDrink.getStock());
        }

        System.out.println("╚════╩═══════════════════╩═══════════════════════════╩═════════╩═══════════════╝");
    }

    // Helper method to truncate string to specified length
    private String truncateString(String str, int length) {
        if (str == null) return "";
        if (str.length() <= length) {
            return String.format("%-" + length + "s", str);
        }
        return str.substring(0, length - 2) + "..";
    }

    public List<FoodAndDrink> getFoodAndDrinks() {
        return foodAndDrinks;
    }
}
