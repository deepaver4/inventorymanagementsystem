package com.practice.inventoryservice.kafka;

import com.practice.inventoryservice.model.Inventory;
import com.practice.inventoryservice.model.ProductEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationProducer {

    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(NotificationProducer.class);


    public void updateStock(Inventory inventory) {

        if (inventory.getQuantity() <= 3) {
            ProductEvent event = new ProductEvent(
                    inventory.getId(),
                    inventory.getSkuCode(),
                    inventory.getQuantity(),
                    "Stock is below threshold"
            );

            logger.info("Low stock alert for sku: {}. New quantity: {}",
                    inventory.getSkuCode(), inventory.getQuantity());
            kafkaTemplate.send("low-stock-alert", event);
        }
    }
}

