package com.practice.productservice.model;

public class ProductEventCreated {

    public ProductEventCreated() {}

    public ProductEventCreated(String productId, String skuCode, int quantity) {
        this.productId = productId;
        this.skuCode = skuCode;
        this.quantity = quantity;
    }
    private String productId;
        private String skuCode;
        private int quantity;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
}
