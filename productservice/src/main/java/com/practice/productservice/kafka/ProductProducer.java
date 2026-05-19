package com.practice.productservice.kafka;

import com.practice.productservice.model.Product;
import com.practice.productservice.model.ProductEventCreated;
import com.practice.productservice.model.ProductSKU;
import com.practice.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ProductProducer {

    @Autowired
    KafkaTemplate<String, ProductEventCreated> kafkaTemplate;

    public void sendMsgToTopic(ProductEventCreated productEventCreated) {
        System.out.println("Sending product message to topic : " + productEventCreated);
        kafkaTemplate.send("product-created",productEventCreated );
    }

}
