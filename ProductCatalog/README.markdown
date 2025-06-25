# Product Catalog Microservice

A simple Spring Boot microservice for managing product information (name, price, description) using REST APIs and MySQL database integration.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Notes](#notes)

## Overview
This microservice provides CRUD (Create, Read, Update, Delete) operations for managing products in a catalog. It uses Spring Boot for the application framework, Spring Data JPA for database interactions, and MySQL for persistent storage. The project is designed to be minimal and beginner-friendly, focusing on core functionality without unnecessary complexity.

## Features
- Create a new product with name, price, and description.
- Retrieve all products or a single product by ID.
- Update an existing product's details.
- Delete a product by ID.
- Basic validation (e.g., non-negative price, non-empty name).

## Tech Stack
- **Spring Boot**: 3.3.4 (for rapid development and embedded server)
- **Spring Data JPA**: For database operations
- **MySQL**: Persistent database
- **Maven**: Build tool
- **Java**: 17

## Prerequisites
- **Java 17**: Ensure JDK 17 is installed.
- **Maven**: Install Maven for dependency management and building the project.
- **MySQL**: Install MySQL server (e.g., MySQL 8.0) and ensure it's running.
- **IDE**: Optional (e.g., IntelliJ IDEA, Eclipse) for easier development.

## Setup
1. **Clone or Create the Project**:
   - Create a directory named `product-catalog`.
   - Add the project files as per the structure below or clone from a repository if available.

2. **MySQL Setup**:
   - Start your MySQL server.
   - Create a database named `productdb`:
     ```sql
     CREATE DATABASE productdb;
     ```
   - Update the `src/main/resources/application.properties` file with your MySQL credentials (default assumes `username: root`, `password: password`):
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/productdb
     spring.datasource.username=root
     spring.datasource.password=password
     spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
     spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
     spring.jpa.hibernate.ddl-auto=update
     ```

3. **Dependencies**:
   - Ensure the `pom.xml` includes the following dependencies:
     - `spring-boot-starter-web`
     - `spring-boot-starter-data-jpa`
     - `mysql-connector-j`
     - `spring-boot-starter-test` (for testing)
   - Run `mvn clean install` to download dependencies.

## Running the Application
1. Navigate to the `product-catalog` directory in a terminal.
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Start the application:
   ```bash
   mvn spring-boot:run
   ```
4. The application will run on `http://localhost:8080`.

## API Endpoints
The microservice exposes REST APIs under `/api/products`. Use tools like Postman or `curl` to test them.

| Method | Endpoint             | Description                     | Request Body Example                              |
|--------|----------------------|---------------------------------|--------------------------------------------------|
| POST   | `/api/products`      | Create a new product            | `{"name":"Laptop","price":999.99,"description":"High-end laptop"}` |
| GET    | `/api/products`      | Get all products                | -                                                |
| GET    | `/api/products/{id}` | Get a product by ID             | -                                                |
| PUT    | `/api/products/{id}` | Update a product by ID          | `{"name":"Laptop","price":1099.99,"description":"Updated laptop"}` |
| DELETE | `/api/products/{id}` | Delete a product by ID          | -                                                |

**Example Commands**:
- Create a product:
  ```bash
  curl -X POST http://localhost:8080/api/products -H "Content-Type: application/json" -d '{"name":"Laptop","price":999.99,"description":"High-end laptop"}'
  ```
- Get all products:
  ```bash
  curl http://localhost:8080/api/products
  ```
- Get a product by ID:
  ```bash
  curl http://localhost:8080/api/products/1
  ```
- Update a product:
  ```bash
  curl -X PUT http://localhost:8080/api/products/1 -H "Content-Type: application/json" -d '{"name":"Laptop","price":1099.99,"description":"Updated laptop"}'
  ```
- Delete a product:
  ```bash
  curl -X DELETE http://localhost:8080/api/products/1
  ```

## Testing
- **API Testing**: Use Postman or `curl` to test the endpoints above.
- **Database Verification**:
  - Connect to MySQL:
    ```sql
    USE productdb;
    SELECT * FROM product;
    ```
  - Verify that product data is persisted correctly.
- **Unit Tests**: The project includes `spring-boot-starter-test`. You can add unit tests in `src/test/java` for the service and controller layers.

## Project Structure
```
product-catalog/
├── src/
│   ├── main/
│   │   ├── java/com/example/productcatalog/
│   │   │   ├── ProductCatalogApplication.java   # Main application
│   │   │   ├── model/
│   │   │   │   └── Product.java                # Product entity
│   │   │   ├── repository/
│   │   │   │   └── ProductRepository.java      # JPA repository
│   │   │   ├── service/
│   │   │   │   └── ProductService.java         # Business logic
│   │   │   ├── controller/
│   │   │   │   └── ProductController.java      # REST API endpoints
│   │   ├── resources/
│   │       └── application.properties          # MySQL configuration
├── pom.xml                                     # Maven dependencies
├── README.md                                   # This file
```

## Notes
- **Simplicity**: The project avoids complex features like authentication or advanced validation to remain beginner-friendly.
- **Database**: Uses MySQL for persistence. Ensure the database and credentials are correctly configured in `application.properties`.
- **Security**: For production, secure MySQL credentials (e.g., use environment variables) and avoid `spring.jpa.hibernate.ddl-auto=update` to prevent unintended schema changes.
- **Extensibility**: Add features like pagination, filtering, or authentication by extending the service and controller layers.
- **Troubleshooting**:
  - If MySQL connection fails, verify the server is running, the database exists, and credentials are correct.
  - Check Maven dependencies with `mvn dependency:tree` if build issues occur.

For further assistance, contact the project maintainer or refer to the [Spring Boot documentation](https://spring.io/projects/spring-boot).