import java.util.*;
public class Main {
    public static void main(String[] args) {
        Drink[] drinks = new Drink[3];
        drinks[0] = new Coffee(1, "Bạc sỉu", 30000, true, "Coffee");
        drinks[1] = new FruitJuice(2, "Nước cam", 40000, 10, "FruitJuice");
        drinks[2] = null;

        for (int i = 0; i < drinks.length; i++) {
            if(drinks[i] != null){
                drinks[i].displayInfo();
                System.out.println("Thành tiền: " + drinks[i].calculatePrice());
                if(drinks[i].type == "FruitJuice"){
                    drinks[i].mix();
                }
            }
        }
    };
}