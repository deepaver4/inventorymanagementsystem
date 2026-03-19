package com.practice.orderservice.model;

public enum OrderStatus {
    NEW,
    VALIDATED,
    INVENTORY_RESERVED,
    COMPLETED,
    FAILED,
    CANCELLED
}
