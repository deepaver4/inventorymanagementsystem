package com.practice.inventoryservice.service;

import com.practice.inventoryservice.model.Inventory;
import com.practice.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryService {

    @Autowired
    public InventoryRepository inventoryRepository;

    public Inventory getInventoryBySkuCode(String skuCode){
        return inventoryRepository.findBySkuCode(skuCode).orElse(null);
    }

    public List<Inventory> getInventory(){
        return inventoryRepository.findAll();
    }

    public Map<String, Boolean> checkInventoryAvailability(List<String> skuCodes){
        List<Inventory> inventories = inventoryRepository.findBySkuCodeIn(skuCodes);
        Map<String, Boolean> availability = new HashMap<>();
        for (String skuCode : skuCodes) {
            boolean available = inventories.stream()
                    .anyMatch(inv -> inv.getSkuCode().equals(skuCode) && inv.getQuantity() > 0);
            availability.put(skuCode, available);
        }
        return availability;
    }

    public boolean isInStock(String skuCode, int quantity) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode).orElse(null);

        return inventory != null && inventory.getQuantity() >= quantity;
    }

    public Inventory updateInventory(@RequestBody Inventory inventory) {
        return inventoryRepository.save(inventory);
    }
}
