package com.practice.orderservice.kafka;

import com.practice.orderservice.model.Order;
import com.practice.orderservice.model.OrderEventCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafkaOrder")
public class KafkaOrderServiceController {

    @Autowired
    private KafkaOrderProducer kafkaOrderProducer;

    @GetMapping("/orderMessage")
    public void getOrderMessageFromClient(@RequestParam OrderEventCreated orderEventCreated) {

        kafkaOrderProducer.sendMessageToTopic(orderEventCreated);
    }
}
