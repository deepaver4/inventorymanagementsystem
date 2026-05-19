package com.practice.inventoryservice.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", url = "http://localhost:8080")
public interface InventoryClient {

        @GetMapping("/inventory/check")
        Boolean isInStock(
                @RequestParam("skuCode") String skuCode,
                @RequestParam("quantity") int quantity
        );
}

