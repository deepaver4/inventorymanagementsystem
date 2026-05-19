# Order Service

A microservice for managing orders in an inventory management system, built with Spring Boot. This service handles order creation, validation, updates, and integrates with inventory and messaging systems.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Features](#features)
- [API Endpoints](#api-endpoints)
- [Data Models](#data-models)
- [Configuration](#configuration)
- [Dependencies](#dependencies)
- [Getting Started](#getting-started)
- [Build and Run](#build-and-run)

## Overview

The Order Service is a Spring Boot application that provides RESTful APIs for order management. It integrates with:
- **MongoDB** for data persistence
- **Inventory Service** for stock validation via Feign client
- **Kafka** for event-driven messaging

## Architecture

```
Order Service
├── Controller Layer (OrderController)
├── Service Layer (OrderService)
├── Repository Layer (OrderRepository)
├── Kafka Integration (KafkaOrderProducer)
├── External Integration (InventoryClient - Feign)
└── Data Models (Order, OrderItem, OrderStatus, OrderEventCreated)
```

## Features

- **Order Management**: Create, read, update, and delete orders
- **Inventory Validation**: Checks stock availability before order creation
- **Event-Driven Messaging**: Publishes order events to Kafka
- **Customer Order History**: Retrieve orders by customer ID
- **Bulk Order Retrieval**: Fetch multiple orders by IDs

## API Endpoints

### Get All Orders
```http
GET /orders/getAllOrders
```
Returns a list of all orders.

### Create Order
```http
POST /orders/saveOrder
Content-Type: application/json

{
  "customerId": "customer123",
  "items": [
    {
      "skuCode": "SKU001",
      "quantity": 2,
      "price": 29.99
    }
  ],
  "status": "NEW",
  "totalAmount": 59.98,
  "orderDate": "2024-01-15T10:30:00",
  "lastUpdated": "2024-01-15T10:30:00"
}
```

### Find Orders by IDs
```http
GET /orders/findAllByOrderIds?orderIds=order1,order2,order3
```

### Find Order by ID
```http
GET /orders/findById?id=order123
```

### Update Order
```http
PUT /orders/updateOrder
Content-Type: application/json

{
  "orderId": "order123",
  "customerId": "customer123",
  "items": [...],
  "status": "COMPLETED",
  "totalAmount": 59.98,
  "lastUpdated": "2024-01-15T11:00:00"
}
```

### Delete Order
```http
DELETE /orders/deleteOrder?id=order123
```

### Find Orders by Customer ID
```http
GET /orders/findAllByCustomerId?userId=customer123
```

## Data Models

### Order
```java
{
  "orderId": "String",           // MongoDB document ID
  "customerId": "String",        // Customer identifier
  "items": "List<OrderItem>",    // List of order items
  "status": "OrderStatus",       // Order status enum
  "totalAmount": "BigDecimal",   // Total order amount
  "orderDate": "LocalDateTime",  // Order creation timestamp
  "lastUpdated": "LocalDateTime" // Last update timestamp
}
```

### OrderItem
```java
{
  "skuCode": "String",        // Product SKU code
  "quantity": "int",          // Quantity ordered
  "price": "BigDecimal"       // Price per unit
}
```

### OrderStatus Enum
```java
enum OrderStatus {
  NEW,
  VALIDATED,
  INVENTORY_RESERVED,
  COMPLETED,
  FAILED,
  CANCELLED
}
```

### OrderEventCreated
```java
{
  "skuCode": "String",        // Product SKU code
  "quantity": "int",          // Quantity ordered
  "price": "BigDecimal"       // Price per unit
}
```

## Configuration

### Application Properties
```properties
# Application
spring.application.name=orderservice
server.port=8083

# MongoDB Configuration
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=orders
spring.data.mongodb.username=admin
spring.data.mongodb.password=password
spring.data.mongodb.uri=mongodb://localhost:27017/orders
spring.data.mongodb.auto-index-creation=true

# Kafka Configuration
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
```

### Environment Variables
- `MONGODB_URI`: MongoDB connection string
- `KAFKA_BOOTSTRAP_SERVERS`: Kafka broker addresses
- `INVENTORY_SERVICE_URL`: Inventory service endpoint (configured in InventoryClient)

## Dependencies

### Core Dependencies
- **Spring Boot Starter Web**: RESTful web services
- **Spring Boot Starter Data MongoDB**: MongoDB integration
- **Spring Cloud Starter OpenFeign**: Declarative REST client
- **Spring Boot Starter Kafka**: Apache Kafka integration
- **Project Lombok**: Java boilerplate reduction

### Development Dependencies
- **Spring Boot Starter Test**: Testing framework
- **Spring Boot Starter Web MVC Test**: Web layer testing
- **Spring Boot Starter MongoDB Test**: MongoDB testing
- **Spring Boot Starter Kafka Test**: Kafka testing

## Getting Started

### Prerequisites
- Java 17 or higher
- MongoDB (running on localhost:27017)
- Apache Kafka (running on localhost:9092)
- Inventory Service (running on localhost:8082)

### Installation

1. Clone the repository
2. Navigate to the orderservice directory
3. Ensure MongoDB and Kafka are running
4. Ensure Inventory Service is running

## Build and Run

### Using Maven

```bash
# Build the application
mvn clean compile

# Run the application
mvn spring-boot:run

# Run tests
mvn test
```

### Using Maven Wrapper

```bash
# Build the application
./mvnw clean compile

# Run the application
./mvnw spring-boot:run

# Run tests
./mvnw test
```

### Docker (if applicable)

```bash
# Build Docker image
docker build -t orderservice .

# Run with Docker
docker run -p 8083:8083 orderservice
```

## Key Components

### OrderService
Core business logic service that handles:
- Order validation and stock checking
- Order persistence
- Event publishing to Kafka

### OrderController
REST controller providing HTTP endpoints for order operations.

### InventoryClient
Feign client for communicating with the Inventory Service to check stock availability.

### KafkaOrderProducer
Publishes order creation and update events to Kafka topic "order-created".

### OrderRepository
MongoDB repository interface for Order entity operations.

## Error Handling

The service implements basic error handling:
- **Stock Validation**: Throws RuntimeException if items are out of stock
- **Database Operations**: MongoDB exceptions are propagated
- **External Service Calls**: Feign client handles HTTP errors

## Monitoring and Logging

- Spring Boot Actuator endpoints available for health checks
- Structured logging with Spring Boot's default logging framework
- Kafka producer metrics available through Spring Boot metrics

## Contributing

1. Follow standard Spring Boot project structure
2. Write unit tests for new features
3. Update documentation for API changes
4. Ensure code compiles and tests pass before submitting

## License

This project is licensed under the MIT License.
