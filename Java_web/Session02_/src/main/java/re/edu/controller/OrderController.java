package re.edu.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class OrderController {

    @Autowired
    private ServletContext application;

    @GetMapping("/orders")
    public String orders(HttpSession session,
                         Model model) {

        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/login";
        }

        List<Map<String, Object>> orders = new ArrayList<>();

        orders.add(createOrder("ORD01", "Laptop", 15000000));
        orders.add(createOrder("ORD02", "Chuột", 200000));
        orders.add(createOrder("ORD03", "Bàn phím", 500000));

        model.addAttribute("orders", orders);

        // chống race condition
        synchronized (application) {
            Integer count = (Integer) application.getAttribute("totalViewCount");
            if (count == null) count = 0;
            count++;
            application.setAttribute("totalViewCount", count);
        }

        return "orders";
    }

    private Map<String, Object> createOrder(String id, String name, double price) {
        Map<String, Object> order = new HashMap<>();
        order.put("id", id);
        order.put("name", name);
        order.put("price", price);
        order.put("date", new Date());
        return order;
    }
}
