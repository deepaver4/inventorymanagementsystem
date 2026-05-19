package com.practice.orderservice.controller;

import com.practice.orderservice.model.Order;
import com.practice.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    public OrderService orderService;

    @GetMapping("/getAllOrders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping("/saveOrder")
    public Order saveOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @GetMapping("/findAllByOrderIds")
    public List<Order> findAllByOrderIds(@RequestParam List<String> orderIds){
        return orderService.findAllByOrderId(orderIds);
    }

    @GetMapping("/findById")
    public Order findById(@RequestParam String id){
        return orderService.findById(id);
    }

    @PutMapping("/updateOrder")
    public void updateOrderById(@RequestBody Order order) {
        orderService.updateOrder(order);
    }

    @DeleteMapping("/deleteOrder")
    public void cancelOrderById(@RequestParam String id) {
        orderService.cancelOrder(id);
    }

    @GetMapping("/findAllByCustomerId")
    public List<Order> findAllByCustomerId(String userId) {
        return orderService.findAllByCustomerId(userId);
    }
}
