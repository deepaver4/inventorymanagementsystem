package com.practice.orderservice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class OrderItem {
    private String skuCode;     // SKU being ordered
    private int quantity;       // Quantity ordered
    private BigDecimal price;   // Price per SKU
}
