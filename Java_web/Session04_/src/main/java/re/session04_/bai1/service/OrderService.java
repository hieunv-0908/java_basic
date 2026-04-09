package re.session04_.bai1.service;

import org.springframework.stereotype.Service;
import re.session04_.bai1.repository.OrderRepository;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public String getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public String getOrderById(Long id) {
        return orderRepository.getOrderById(id);
    }

}