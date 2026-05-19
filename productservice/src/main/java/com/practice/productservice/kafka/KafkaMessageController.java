package com.practice.productservice.kafka;

import com.practice.productservice.model.Product;
import com.practice.productservice.model.ProductEventCreated;
import com.practice.productservice.model.ProductSKU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafkaProduct")
public class KafkaMessageController {

    @Autowired
    ProductProducer productProducer;

    @GetMapping("/producerMsg")
    public void getMessageFromClient(@RequestParam ProductEventCreated productEventCreated) {
        productProducer.sendMsgToTopic(productEventCreated);
    }
}
