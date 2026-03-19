package com.practice.inventoryservice.kafka;

import com.practice.inventoryservice.model.Inventory;
import com.practice.inventoryservice.model.ProductEvent;
import com.practice.inventoryservice.repository.InventoryRepository;
import com.practice.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ProductConsumer implements ConsumerSeekAware {

    private static final Logger logger = LoggerFactory.getLogger(ProductConsumer.class);
    private final InventoryRepository inventoryRepository;

    @Autowired
    InventoryService inventoryService;

    public ProductConsumer(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @KafkaListener(topics = "product-created", groupId = "inventory-group")
    public void consumeProductCreated(ProductEvent product) {

            Inventory inventory = new Inventory();
            inventory.setId(product.getProductId());
            inventory.setSkuCode(product.getSkuCode());
            inventory.setQuantity(product.getQuantity());
            inventoryRepository.save(inventory);
            logger.info("Successfully consumed and saved product: {} with sku: {}",
                       product.getProductId(), product.getSkuCode());

    }

    @KafkaListener(topics = "order-created", groupId = "inventory-group")
    public void consumeOrderCreated(ProductEvent orderEvent) {

        logger.info("Received order event: {} for orderId: {}"+orderEvent.getSkuCode());
        inventoryRepository.findBySkuCode(orderEvent.getSkuCode()).ifPresent(inventory -> {
            inventory.setQuantity(inventory.getQuantity() - orderEvent.getQuantity());
            inventoryRepository.save(inventory);
            logger.info("Updated inventory for sku: {}. New quantity: {}",
                    orderEvent.getSkuCode(), inventory.getQuantity());
        });

    }
}
