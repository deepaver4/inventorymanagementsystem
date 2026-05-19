# Inventory Service

A microservice component of the Inventory Management System that handles inventory management, stock tracking, and inventory operations using Spring Boot, MongoDB, and Apache Kafka.

## Overview

The Inventory Service is responsible for:
- Managing product inventory and stock levels
- Tracking inventory availability
- Processing product creation and order events from Kafka
- Providing inventory check and update operations
- Sending stock update notifications

## Technology Stack

- **Framework**: Spring Boot 4.0.3
- **Language**: Java 17
- **Database**: MongoDB
- **Message Broker**: Apache Kafka
- **ORM**: Spring Data MongoDB
- **Client Communication**: OpenFeign
- **Build Tool**: Maven
- **Additional Libraries**: Lombok, Jackson

## Project Structure

```
inventoryservice/
├── src/
│   ├── main/
│   │   ├── java/com/practice/inventoryservice/
│   │   │   ├── controller/        # REST API endpoints
│   │   │   ├── service/           # Business logic
│   │   │   ├── repository/        # MongoDB data access layer
│   │   │   ├── model/             # Entity classes
│   │   │   ├── kafka/             # Kafka consumers and producers
│   │   │   └── feignclient/       # External service clients
│   │   └── resources/
│   │       └── application.properties  # Configuration
│   └── test/
│       └── java/                  # Unit tests
├── pom.xml                        # Maven dependencies
└── README.md                      # This file
```

## Key Components

### Models

**Inventory**
- `id`: Unique identifier
- `skuCode`: Stock Keeping Unit code
- `quantity`: Current stock quantity

**ProductEvent**
- Event model for Kafka message consumption

### REST API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/inventory/getInventory` | Retrieve all inventory items |
| GET | `/inventory/getInventoryBySkuCode` | Get inventory for a specific SKU |
| POST | `/inventory/checkInventoryAvailability` | Check availability of multiple SKUs |
| PUT | `/inventory/updateInventory` | Update inventory information |
| GET | `/inventory/check` | Check if a specific quantity is in stock |

### Service Layer

**InventoryService**
- `getInventory()`: Fetch all inventory records
- `getInventoryBySkuCode()`: Retrieve inventory by SKU code
- `checkInventoryAvailability()`: Verify if SKUs are available
- `isInStock()`: Check stock availability for a specific quantity
- `updateInventory()`: Update inventory records

### Kafka Integration

**ProductConsumer**
- Listens to `product-created` topic: Creates inventory records for new products
- Listens to `order-created` topic: Reduces inventory based on orders
- Listens to `low-stock-alert` topic: Handles low stock notifications

**NotificationProducer**
- Publishes stock update notifications

## Configuration

The service is configured via `application.properties`:

```properties
# Server
server.port=8080

# MongoDB
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=inventory
spring.data.mongodb.username=admin
spring.data.mongodb.password=password

# Kafka
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=inventory-group

# Feign Client
feign.client.config.default.connect-timeout=5000
feign.client.config.default.read-timeout=5000
```

## Prerequisites

- Java 17 or higher
- MongoDB running on localhost:27017
- Apache Kafka running on localhost:9092
- Maven 3.6 or higher

## Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd inventorymanagementsystem/inventoryservice
```

### 2. Configure MongoDB
Ensure MongoDB is running:
```bash
mongod
```

Create a database and user:
```javascript
use inventory
db.createUser({
  user: "admin",
  pwd: "password",
  roles: ["readWrite"]
})
```

### 3. Configure Kafka
Ensure Kafka is running:
```bash
# Start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

# Start Kafka Server
bin/kafka-server-start.sh config/server.properties
```

Create required topics:
```bash
bin/kafka-topics.sh --create --topic product-created --bootstrap-server localhost:9092
bin/kafka-topics.sh --create --topic order-created --bootstrap-server localhost:9092
bin/kafka-topics.sh --create --topic low-stock-alert --bootstrap-server localhost:9092
```

### 4. Build and Run
```bash
# Build
mvn clean install

# Run
mvn spring-boot:run
```

The service will start on `http://localhost:8080`

## API Usage Examples

### Get All Inventory
```bash
curl -X GET http://localhost:8080/inventory/getInventory
```

### Get Inventory by SKU Code
```bash
curl -X GET "http://localhost:8080/inventory/getInventoryBySkuCode?skuCode=SKU001"
```

### Check Inventory Availability
```bash
curl -X POST http://localhost:8080/inventory/checkInventoryAvailability \
  -H "Content-Type: application/json" \
  -d '["SKU001", "SKU002"]'
```

### Check Stock Availability
```bash
curl -X GET "http://localhost:8080/inventory/check?skuCode=SKU001&quantity=5"
```

### Update Inventory
```bash
curl -X PUT http://localhost:8080/inventory/updateInventory \
  -H "Content-Type: application/json" \
  -d '{
    "id": "123",
    "skuCode": "SKU001",
    "quantity": 100
  }'
```

## Kafka Event Flow

1. **Product Created Event**: When a new product is created, the service receives a `product-created` event and creates an inventory record
2. **Order Created Event**: When an order is placed, the `order-created` event triggers inventory reduction
3. **Stock Update Notification**: After inventory updates, a notification is published for external systems

## Error Handling

The service handles the following common errors:
- Missing inventory records (returns null)
- Invalid requests (HTTP 400)
- Database connection issues
- Kafka serialization/deserialization errors

## Dependencies

Key Maven dependencies:
- `spring-boot-starter-data-mongodb`: MongoDB integration
- `spring-boot-starter-webmvc`: REST API support
- `spring-boot-starter-kafka`: Kafka consumer/producer support
- `spring-cloud-starter-openfeign`: Service-to-service communication
- `lombok`: Code generation utility
- `jackson-databind`: JSON processing

## Testing

Run unit tests:
```bash
mvn test
```

## Troubleshooting

### MongoDB Connection Issues
- Ensure MongoDB is running on localhost:27017
- Verify credentials in application.properties

### Kafka Connection Issues
- Check Kafka broker is running on localhost:9092
- Verify topics exist
- Check Kafka consumer group configuration

### Bean Not Found Errors
- Ensure MongoDB is properly configured
- Check `spring.data.mongodb.uri` is correct

## Future Enhancements

- Implement caching for frequently accessed inventory
- Add inventory history tracking
- Implement stock reorder alerts
- Add batch inventory updates
- Implement inventory forecasting

## Contributing

When contributing, please:
1. Follow Spring Boot best practices
2. Add unit tests for new features
3. Update this README with any changes
4. Maintain consistency with existing code style

## License

This project is part of the Inventory Management System.

## Support

For issues or questions, please refer to the main project documentation or contact the development team.

