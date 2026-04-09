package re.session04_.bai1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import re.session04_.bai1.service.OrderService;

@Controller
public class LegacyController {
    public OrderService orderService;

    public LegacyController(){
        this.orderService = new OrderService();
    }

    @RequestMapping(value = "/bai1/orders", method = RequestMethod.GET)
    @ResponseBody
    public String getAllOrders() {
        return orderService.getAllOrders();
    }

    @RequestMapping(value = "/bai1/orders/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
