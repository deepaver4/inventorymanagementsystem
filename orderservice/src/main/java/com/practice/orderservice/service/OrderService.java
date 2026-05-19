package com.practice.orderservice.service;

import com.practice.orderservice.client.InventoryClient;
import com.practice.orderservice.kafka.KafkaOrderProducer;
import com.practice.orderservice.model.OrderEventCreated;
import com.practice.orderservice.model.OrderItem;
import com.practice.orderservice.repository.OrderRepository;
import com.practice.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public KafkaOrderProducer kafkaOrderProducer;

    @Autowired
    public InventoryClient inventoryClient;

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    public Order saveOrder(Order order){
        validateStock(order.getItems());
        Order savedOrder = orderRepository.save(order);
        OrderEventCreated event = new OrderEventCreated(savedOrder.getItems().get(0).getSkuCode(),
                savedOrder.getItems().get(0).getQuantity() , savedOrder.getItems().get(0).getPrice());
        kafkaOrderProducer.sendMessageToTopic(event);
        return savedOrder;
    }

    public void validateStock(List<OrderItem> items) {
        for (OrderItem item : items) {
            Boolean inStock = inventoryClient.isInStock(
                    item.getSkuCode(),
                    item.getQuantity()
            );

            if (Boolean.FALSE.equals(inStock)) {
                throw new RuntimeException("Out of stock for SKU: " + item.getSkuCode());
            }
        }
    }

    public void updateOrder(Order order){
        Order savedOrder = orderRepository.save(order);
        System.out.println("Order will be Updated Successfully");
        List<OrderItem> orderItems = savedOrder.getItems();
        OrderEventCreated orderEventCreated = new OrderEventCreated(orderItems.get(0).getSkuCode(),
                      orderItems.get(0).getQuantity(),
                      orderItems.get(0).getPrice());
                      System.out.println("Order Updated Successfully");
        kafkaOrderProducer.sendMessageToTopic(orderEventCreated);
    }

    public void cancelOrder(String id){
        orderRepository.deleteById(id);
    }

    public List<Order> findAllByOrderId(List<String> orderIds){
           return orderRepository.findAllById(orderIds).stream().toList();
    }

    public Order findById(String id){
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> findAllByCustomerId(String userId) {
        return orderRepository.findAll().stream().
                filter(order -> order.getCustomerId().equals(userId)).
                collect(Collectors.toList());
    }

}
