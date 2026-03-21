import model.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    void testCalculateTotal() {

        Order order = new Order("ORD1");

        MenuItem burger = new Food("F1", "Burger", 20, "FastFood");
        MenuItem cola = new Drink("D1", "Coca", 10, "L");

        order.addItem(burger, 2);   // 40
        order.addItem(cola, 1);     // 10

        double total = order.calculateTotal();

        assertEquals(50, total);

    }

}