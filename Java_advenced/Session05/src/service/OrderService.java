package service;

import service.InvalidOrderIdException;
import service.InsufficientStockException;
import model.MenuItem;
import model.Order;
import model.OrderStatus;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private List<Order> orders = new ArrayList<>();

    public Order createOrder(){

        Order order = new Order("ORD" + (orders.size() + 1));
        orders.add(order);

        return order;
    }

    public void addItemToOrder(String orderId, MenuItem item, int qty)
            throws InvalidOrderIdException, InsufficientStockException {

        Order order = getOrderById(orderId);

        if(order == null){
            throw new InvalidOrderIdException("Order ID not found: " + orderId);
        }

        order.addItem(item, qty);
    }

    public void cancelOrder(String orderId) throws InvalidOrderIdException {

        Order order = getOrderById(orderId);

        if(order == null){
            throw new InvalidOrderIdException("Order not found");
        }

        order.setStatus(OrderStatus.CANCELLED);
    }

    public Order getOrderById(String orderId){

        for(Order order : orders){
            if(order.getId().equals(orderId)){
                return order;
            }
        }

        return null;
    }

    public List<Order> getOrders(){
        return orders;
    }
}