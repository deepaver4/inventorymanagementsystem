# Product Service

This microservice manages products for the Inventory Management System. It provides REST endpoints to create, list and delete products, persists product data in MongoDB, and publishes product-created events to Kafka.

---

Checklist
- [x] Summarize what the service does
- [x] Document endpoints implemented in `ProductController`
- [x] Describe domain models found in `model/`
- [x] Explain Kafka integration (producer & topic)
- [x] Show configuration keys from `application.properties`
- [x] Provide build, run and troubleshooting guidance

---

Quick facts
- Module path: `productservice`
- Main class: `com.practice.productservice.ProductserviceApplication`
- Server port (default): `8081`
- MongoDB database: `products` (configured in `application.properties`)
- Kafka topic used: `product-created`

## Table of Contents
- Overview
- Project layout
- Models
- REST API
- Kafka integration
- Configuration (important properties)
- Build & Run
- Troubleshooting
- Contributing

## Overview

The Product Service is a Spring Boot application (Java 17) that:
- Stores product information in MongoDB
- Exposes REST endpoints to create, query and delete products
- Publishes product-created events to Kafka so other services (like Inventory) can react

The service uses:
- Spring Boot (webmvc)
- Spring Data MongoDB
- Spring for Apache Kafka
- OpenFeign (enabled in the main class; used by other services that call this one)
- Lombok (for model getters/setters)

## Project layout (relevant files)
```
productservice/
├── src/main/java/com/practice/productservice/
│   ├── controller/ProductController.java
│   ├── service/ProductService.java
│   ├── repository/ProductRepository.java
│   ├── model/Product.java
│   ├── model/ProductSKU.java
│   ├── model/ProductEventCreated.java
│   ├── kafka/ProductProducer.java
│   ├── config/MongoConfig.java        # present but currently empty (placeholder)
│   └── config/KafkaConfig.java        # present but currently empty (placeholder)
├── src/main/resources/application.properties
└── pom.xml
```

## Models

Product (stored in collection `products`)
- `id` (String): MongoDB document id
- `name` (String)
- `price` (double)
- `productSKU` (ProductSKU): contains SKU info

ProductSKU
- `skuCode` (String)
- `quantity` (int)

ProductEventCreated
- Simple POJO used for Kafka events with fields: `productId`, `skuCode`, `quantity`

## REST API
Base path: `/products`

- POST `/products/create`
  - Request body: JSON representation of `Product`
  - Behavior: saves product to MongoDB, publishes a `ProductEventCreated` to Kafka
  - Response: saved `Product` object (with generated `id`)

- GET `/products/getAllProducts`
  - Returns: list of all products

- GET `/products/getProduct?id={id}`
  - Returns: product with the given id or `null` if not found

- DELETE `/products/deleteProduct?id={id}`
  - Deletes product by id (no body response)

- GET `/products/getCountOfAllProduct`
  - Returns: Map of SKU -> total quantity across stored products

Example curl (PowerShell)

```powershell
# create product
$body = @'
{
  "name": "Example Product",
  "price": 19.99,
  "productSKU": { "skuCode": "SKU123", "quantity": 10 }
}
'@
curl -Method POST -Uri http://localhost:8081/products/create -Body $body -ContentType 'application/json'

# list products
curl http://localhost:8081/products/getAllProducts
```

## Kafka integration

- Producer: `com.practice.productservice.kafka.ProductProducer`
  - Uses `KafkaTemplate<String, ProductEventCreated>` to send messages
  - Topic used: `product-created`
  - Message type: `ProductEventCreated` (JSON serialized by `JsonSerializer` when configured)

Notes for consumers:
- Consumers that read `product-created` must configure `spring.kafka.consumer.value-deserializer` to `org.springframework.kafka.support.serializer.JsonDeserializer` and set `spring.kafka.consumer.properties.spring.json.trusted.packages=*` (or the appropriate package) so the `ProductEventCreated` class can be deserialized.

## Configuration (from `src/main/resources/application.properties`)
Relevant properties used by this module:

```properties
spring.application.name=productservice
server.port=8081

# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/products
spring.data.mongodb.database=products
spring.data.mongodb.auto-index-creation=true

# Kafka (producer)
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
```

If you need custom `MongoTemplate` or Kafka beans, `config/MongoConfig.java` and `config/KafkaConfig.java` are present as placeholders.

## Build & Run
Prerequisites
- Java 17+
- Maven 3.6+
- MongoDB running (default: localhost:27017)
- Kafka broker available (default: localhost:9092)

From the `productservice` directory:

PowerShell examples (using the wrapper if present):

```powershell
# build
.\mvnw.cmd clean package
# or with installed maven
mvn clean package

# run
.\mvnw.cmd spring-boot:run
# or
mvn spring-boot:run
```

The service will start on `http://localhost:8081` by default.

## Creating Kafka topics (if you manage Kafka locally)

Use Kafka's topic creation script that comes with your Kafka distribution. Example (bash):

```bash
bin/kafka-topics.sh --create --topic product-created --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
```

Adjust paths/commands for your OS/environment.

## Troubleshooting

1) Failure to resolve bean `mongoTemplate` / repository bean creation errors
- Symptom: exceptions like "Cannot resolve reference to bean 'mongoTemplate' while setting bean property 'mongoOperations'" when Spring tries to create repository beans.
- Checks & fixes:
  - Confirm `spring-boot-starter-data-mongodb` is on the classpath (it is in `pom.xml`).
  - Verify `spring.data.mongodb.uri` is set correctly in `application.properties` and points to a reachable MongoDB instance.
  - If you added a custom `MongoConfig`, ensure it defines a `MongoTemplate` bean or does not conflict with Spring Boot autoconfiguration. If `MongoConfig.java` is empty, remove or implement it properly to avoid accidental misconfiguration.
  - Confirm no duplicate or broken Mongo-related bean definitions in other modules when running multiple services together.

2) Kafka serialization / deserialization errors
- Symptom: exceptions like "Can't convert value of class ... to class org.apache.kafka.common.serialization.StringSerializer specified in value.serializer" or ClassNotFoundException for `JsonDeserializer`.
- Checks & fixes:
  - Ensure each app's producer and consumer serializer/deserializer settings align. If you publish JSON objects (POJOs) with `JsonSerializer`, consumers must use `JsonDeserializer`.
  - Consumer properties must include:
    ```properties
    spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
    spring.kafka.consumer.properties.spring.json.trusted.packages=*
    spring.kafka.consumer.properties.spring.json.value.default.type=com.practice.productservice.model.ProductEventCreated
    ```
    and the `spring-kafka` dependency must be present (the project includes `spring-boot-starter-kafka`).
  - If you see ClassNotFoundException for `org.apache.kafka.support.serializer.JsonDeserializer`, that indicates a wrong class name — the correct class is `org.springframework.kafka.support.serializer.JsonDeserializer` (part of Spring Kafka).

3) Other common issues
- Port conflicts: change `server.port` in `application.properties` if `8081` is already used.
- Missing topics: ensure `product-created` topic exists if Kafka does not auto-create topics in your environment.

## Tests
- The module has `spring-boot-starter-kafka-test` and mongo test dependencies. Run tests with:

```powershell
mvn test
```

## Contributing
- Follow the existing code style and module structure.
- Add unit tests for new behavior.
- Keep `application.properties` and README examples in sync.

---

If you'd like, I can also:
- Add README badges (build / jdk / codecov)
- Populate the empty `MongoConfig.java` and `KafkaConfig.java` with minimal, non-conflicting bean definitions
- Add examples of consumer configuration for the Inventory Service that consumes `product-created` events

File created at: `productservice/README.md` (C:\myJavaWorkspace\inventorymanagementsystem\productservice\README.md)

