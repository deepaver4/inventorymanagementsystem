package com.practice.orderservice.kafka;

import com.practice.orderservice.model.OrderEventCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class KafkaOrderProducer {

    @Autowired
    public KafkaTemplate <String, OrderEventCreated> kafkaOrderService;

    public void sendMessageToTopic(OrderEventCreated orderEventCreated){
        kafkaOrderService.send("order-created", orderEventCreated);
    }


}
