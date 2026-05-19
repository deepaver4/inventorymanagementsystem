# Inventory Management System

A microservices-based inventory management system built with Spring Boot, MongoDB, and Apache Kafka for event-driven communication.

## Overview

The system consists of three independent microservices that work together to manage products, orders, and inventory:

- **Product Service** - Manages product catalog and information
- **Order Service** - Handles order processing and management
- **Inventory Service** - Tracks and manages inventory levels

## Services

### Product Service
- **Port:** 8081
- **Database:** MongoDB (products)
- **Responsibilities:**
  - CRUD operations for products
  - Product information management
  - Event publishing for product updates

### Order Service
- **Port:** 8083
- **Database:** MongoDB (orders)
- **Responsibilities:**
  - Order creation and management
  - Order status tracking
  - Integration with inventory and product services

### Inventory Service
- **Port:** 8080
- **Database:** MongoDB (inventory)
- **Responsibilities:**
  - Inventory level tracking
  - Stock management
  - Real-time inventory updates
  - Listens to product events from Kafka

## Architecture Flow

```
┌─────────────────┐
│   Client/API    │
└────────┬────────┘
         │
    ┌────┴────────────────┬──────────────────┐
    │                     │                  │
    ▼                     ▼                  ▼
┌──────────────┐    ┌──────────────┐   ┌──────────────┐
│   Product    │    │    Order     │   │  Inventory   │
│   Service    │    │   Service    │   │   Service    │
│   (8081)     │    │   (8083)     │   │   (8080)     │
└──────┬───────┘    └──────┬───────┘   └──────┬───────┘
       │                   │                  │
       └───────────────────┼──────────────────┘
                     ▼
            ┌─────────────────┐
            │  Apache Kafka   │
            │  Event Broker   │
            └────────┬────────┘
                     │
         ┌───────────┴───────────┐
         ▼                       ▼
    (Product Events)      (Inventory Events)
```

## Technology Stack

- **Framework:** Spring Boot 4.0.3
- **Java Version:** JDK 17
- **Database:** MongoDB
- **Message Broker:** Apache Kafka
- **Service-to-Service Communication:** OpenFeign
- **Build Tool:** Maven
- **Additional Libraries:**
  - Jackson (JSON processing)
  - Lombok (Code generation)
  - Spring Cache

## Prerequisites

- Java 17 or higher
- MongoDB (running on localhost:27017)
- Apache Kafka (running on localhost:9092)
- Maven 3.6+

## Getting Started

### 1. Install Dependencies

Ensure MongoDB and Kafka are running:

```bash
# MongoDB
mongod --dbpath <path_to_data>

# Kafka
bin/kafka-server-start.sh config/server.properties
```

### 2. Build All Services

```bash
mvn clean install
```

### 3. Start Services

From each service directory:

```bash
# Product Service
cd productservice
mvn spring-boot:run

# Order Service (in another terminal)
cd orderservice
mvn spring-boot:run

# Inventory Service (in another terminal)
cd inventoryservice
mvn spring-boot:run
```

## API Endpoints

### Product Service (Port 8081)
- `GET /products` - Get all products
- `POST /products` - Create a new product
- `GET /products/{id}` - Get product by ID
- `PUT /products/{id}` - Update product
- `DELETE /products/{id}` - Delete product

### Order Service (Port 8083)
- `GET /orders` - Get all orders
- `POST /orders` - Create a new order
- `GET /orders/{id}` - Get order by ID
- `PUT /orders/{id}` - Update order

### Inventory Service (Port 8080)
- `GET /inventory` - Get all inventory items
- `GET /inventory/{productId}` - Get inventory for a product
- `PUT /inventory/{productId}` - Update inventory level

## Configuration

Each service has its own `application.properties` file with:

- **MongoDB** credentials and database names
- **Kafka** bootstrap servers and topic configurations
- **Feign** client timeouts for inter-service communication
- **Server ports** for each microservice

## Inter-Service Communication

- **Synchronous:** OpenFeign REST clients for real-time product and inventory lookups
- **Asynchronous:** Kafka topics for event-driven updates between services

## Monitoring & Troubleshooting

- Check MongoDB connection: `mongo mongodb://localhost:27017`
- Verify Kafka: `kafka-console-consumer.sh --bootstrap-servers localhost:9092 --list-topics`
- Check service logs for any configuration issues

## Notes

- Eureka service discovery is currently commented out; services use direct URLs
- Spring Cloud Config is disabled to prevent restart loops
- Default timeouts for Feign clients: 5000ms (connect) / 5000ms (read)

---

For more details on individual services, see the README files in each service directory.
```

This README provides:
- ✅ **Clear Overview** - What each service does
- ✅ **Visual Flow Diagram** - Shows how services interact through Kafka
- ✅ **Technology Stack** - All dependencies and versions
- ✅ **Getting Started Guide** - Prerequisites and startup instructions
- ✅ **API Reference** - Basic endpoint documentation
- ✅ **Configuration Details** - Port numbers, databases, Kafka setup
- ✅ **Communication Patterns** - Both synchronous (Feign) and asynchronous (Kafka)

You can save this as a **README.md** file in the root directory of your `inventorymanagementsystem` folder.
