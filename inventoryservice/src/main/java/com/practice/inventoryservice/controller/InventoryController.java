package com.practice.inventoryservice.controller;

import com.practice.inventoryservice.model.Inventory;
import com.practice.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/getInventory")
    public List<Inventory> getInventory(){
        return inventoryService.getInventory();
    }

    @GetMapping("/getInventoryBySkuCode")
    public Inventory getInventoryBySkuCode(String skuCode){
        return inventoryService.getInventoryBySkuCode(skuCode);
    }

    @PostMapping("/checkInventoryAvailability")
    public Map<String, Boolean> checkInventoryAvailability(List<String> skuCodes){
        return inventoryService.checkInventoryAvailability(skuCodes);
    }

    @PutMapping("/updateInventory")
    public Inventory updateInventory(@RequestBody Inventory inventory) {
        return inventoryService.updateInventory(inventory);
    }

    @GetMapping("/check")
    public Boolean isInStock(
            @RequestParam String skuCode,
            @RequestParam int quantity
    ) {
        return inventoryService.isInStock(skuCode, quantity);
    }

}
