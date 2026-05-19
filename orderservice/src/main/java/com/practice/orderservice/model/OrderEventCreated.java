package com.practice.orderservice.model;

import java.math.BigDecimal;

public class OrderEventCreated {

    public OrderEventCreated() {}

    public OrderEventCreated(String skuCode, int quantity, BigDecimal price) {
        this.skuCode = skuCode;
        this.quantity = quantity;
        this.price = price;
    }
    private String skuCode;
    private int quantity;
    private BigDecimal price;

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() { return price;}

}
