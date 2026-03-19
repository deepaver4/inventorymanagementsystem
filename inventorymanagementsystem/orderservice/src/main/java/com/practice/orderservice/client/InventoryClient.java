package com.practice.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventoryservice", url = "http://localhost:8080")
public interface InventoryClient {

    @GetMapping("/inventory/check")
    Boolean isInStock(
            @RequestParam String skuCode,
            @RequestParam Integer quantity
    );
}

