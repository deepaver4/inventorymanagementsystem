//package com.practice.productservice.kafka;
//
//import com.practice.productservice.model.Product;
//import com.practice.productservice.model.ProductEventCreated;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class Consumer {
//
//    @KafkaListener(topics = "product-created", groupId = "inventory-group")
//    public void listentotheTopic(ProductEventCreated productEventCreated){
//        System.out.println("Message received from topic : " +productEventCreated );
//    }
//}
