# AuthServiceApp

A Spring Boot-based authentication microservice implementing user registration and login functionality using JWT (JSON Web Tokens) for secure authentication. This project uses Spring Security, JPA with MySQL, and provides RESTful endpoints for user management.

## Table of Contents
- [Project Overview](#project-overview)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Application Flow](#application-flow)
- [Setup Instructions](#setup-instructions)
- [Testing with Postman](#testing-with-postman)
- [Troubleshooting](#troubleshooting)
- [Known Issues and Fixes](#known-issues-and-fixes)
- [Contributing](#contributing)
- [License](#license)

## Project Overview
`AuthServiceApp` is a microservice designed to handle user authentication. It provides two main endpoints:
- `/api/auth/register`: Registers a new user with a username, password, email, and default role ("USER").
- `/api/auth/login`: Authenticates a user and returns a JWT token for accessing protected resources.

The application uses Spring Security for authentication and authorization, JWT for stateless session management, and MySQL for persistent storage. It includes a custom `JwtAuthenticationFilter` to validate JWT tokens for protected endpoints.

## Technology Stack
- **Java**: 23.0.1
- **Spring Boot**: 3.4.5
- **Spring Security**: 6.4.5
- **Spring Data JPA**: 3.4.5
- **MySQL**: 8.0 (via `mysql-connector-j` 9.1.0)
- **JWT**: `jjwt` 0.9.1
- **Lombok**: 1.18.38
- **Maven**: Build tool
- **Hibernate**: 6.6.13.Final (ORM)
- **HikariCP**: Connection pooling
- **Postman**: For API testing

## Prerequisites
Before running the project, ensure you have:
- **Java JDK**: 23.0.1 installed (set `JAVA_HOME` and add to `PATH`).
- **Maven**: Installed (`mvn --version` to verify).
- **MySQL**: Running on `localhost:3306` with root user and password `0664` (adjust in `application.properties` if different).
- **Postman**: Installed for API testing.
- **IDE**: IntelliJ IDEA Community Edition 2024.3 (recommended) or any Java IDE.
- **Git**: Optional, for cloning the repository.

## Project Structure
The project follows a standard Spring Boot structure with packages for organization:

```
src/main/java/com/microservices/AuthServiceApp/
├── AuthServiceAppApplication.java       # Main application entry point
├── Config/                              # Security and filter configurations
│   ├── SecurityConfig.java              # Spring Security configuration
│   └── JwtAuthenticationFilter.java     # Custom JWT filter
├── Controller/                          # REST controllers
│   ├── AuthController.java              # Handles /api/auth endpoints
│   └── TestController.java              # Optional test endpoint
├── Dto/                                 # Data Transfer Objects
│   ├── RegisterRequest.java             # DTO for registration
│   ├── LoginRequest.java                # DTO for login
│   └── AuthResponse.java                # DTO for JWT response
├── Entity/                              # JPA entities
│   └── User.java                        # User entity
├── Repository/                          # JPA repositories
│   └── UserRepository.java              # User repository
├── Service/                             # Business logic
│   └── UserService.java                 # User service with UserDetailsService
└── util/                                # Utility classes
    └── JwtUtil.java                     # JWT generation and validation
src/main/resources/
└── application.properties               # Application configuration
```

## Application Flow
1. **Registration** (`/api/auth/register`):
   - Client sends a `POST` request with `username`, `password`, and `email`.
   - `AuthController` calls `UserService.registerUser` to encode the password and save the user to the database with the "USER" role.
   - Returns a success message.
2. **Login** (`/api/auth/login`):
   - Client sends a `POST` request with `username` and `password`.
   - `AuthController` uses `AuthenticationManager` to authenticate credentials.
   - On success, `JwtUtil` generates a JWT token with the username and roles.
   - Returns the JWT in an `AuthResponse`.
3. **Protected Endpoints**:
   - Client includes the JWT in the `Authorization: Bearer <token>` header.
   - `JwtAuthenticationFilter` validates the token and sets the security context.
   - Protected endpoints (e.g., `/api/test`) are accessible if the token is valid.

## Setup Instructions
1. **Clone the Repository** (if applicable):
   ```bash
   git clone <repository-url>
   cd AuthServiceApp
   ```
2. **Configure MySQL**:
   - Start MySQL and create the database:
     ```sql
     CREATE DATABASE authDbMicroservice;
     ```
   - Verify credentials in `application.properties`:
     ```properties
     spring.datasource.username=root
     spring.datasource.password=0664
     ```
3. **Build the Project**:
   ```bash
   mvn clean install
   ```
4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```
   Or run in IntelliJ IDEA by clicking "Run" on `AuthServiceAppApplication`.
5. **Verify Startup**:
   - The application runs on `http://localhost:8080`.
   - Check logs for errors (e.g., database connection issues).

## Testing with Postman
Test the API endpoints using Postman to verify functionality.

### 1. Register User
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/auth/register`
- **Headers**:
  - `Content-Type: application/json`
- **Body** (raw, JSON):
  ```json
  {
      "username": "john",
      "password": "pass123",
      "email": "john@example.com"
  }
  ```
- **Expected Response**: `200 OK`
  ```json
  "User registered successfully"
  ```

### 2. Login User
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/auth/login`
- **Headers**:
  - `Content-Type: application/json`
- **Body** (raw, JSON):
  ```json
  {
      "username": "john",
      "password": "pass123"
  }
  ```
- **Expected Response**: `200 OK`
  ```json
  {
      "token": "eyJhbGciOiJIUzUxMiJ9..."
  }
  ```
- **Note**: Save the JWT token for the next test.

### 3. Test Protected Endpoint
- **Method**: `GET`
- **URL**: `http://localhost:8080/api/test`
- **Headers**:
  - `Authorization: Bearer <your_jwt_token>`
- **Body**: None
- **Expected Response**: `200 OK`
  ```json
  "Hello, john! This is a protected endpoint."
  ```

**Postman Collection**:
- Import the following collection into Postman for easy testing:
  ```json
  {
      "info": {
          "name": "AuthServiceApp Tests",
          "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
      },
      "item": [
          {
              "name": "Register User",
              "request": {
                  "method": "POST",
                  "header": [{"key": "Content-Type", "value": "application/json"}],
                  "body": {
                      "mode": "raw",
                      "raw": "{\"username\": \"john\", \"password\": \"pass123\", \"email\": \"john@example.com\"}"
                  },
                  "url": "http://localhost:8080/api/auth/register"
              }
          },
          {
              "name": "Login User",
              "request": {
                  "method": "POST",
                  "header": [{"key": "Content-Type", "value": "application/json"}],
                  "body": {
                      "mode": "raw",
                      "raw": "{\"username\": \"john\", \"password\": \"pass123\"}"
                  },
                  "url": "http://localhost:8080/api/auth/login"
              }
          },
          {
              "name": "Test Protected Endpoint",
              "request": {
                  "method": "GET",
                  "header": [{"key": "Authorization", "value": "Bearer <your_jwt_token>"}],
                  "url": "http://localhost:8080/api/test"
              }
          }
      ]
  }
  ```

## Troubleshooting
- **Application Fails to Start**:
  - **Circular Dependency**: Ensure `SecurityConfig.java` has no `UserDetailsService` in its constructor. Run `mvn clean install` to clear stale classes.
  - **Database Connection**: Verify MySQL is running and credentials match `application.properties`.
  - Enable debug logging:
    ```properties
    logging.level.org.springframework=DEBUG
    ```
- **403 Forbidden on `/api/auth/login`**:
  - Confirm CSRF is disabled in `SecurityConfig.java` (`.csrf(csrf -> csrf.disable())`).
  - Ensure `/api/auth/**` is permitted (`.requestMatchers("/api/auth/**").permitAll()`).
  - Add CORS configuration if testing from Postman/browser.
  - Check logs with `logging.level.org.springframework.security=DEBUG`.
- **401 Unauthorized**:
  - Verify username/password match a registered user.
  - Check the database (`SELECT * FROM users WHERE username = 'john';`).
- **500 Internal Server Error**:
  - Check logs for database errors (e.g., duplicate username/email).
  - Reset database:
    ```sql
    DROP DATABASE authDbMicroservice;
    CREATE DATABASE authDbMicroservice;
    ```

## Known Issues and Fixes
1. **Circular Dependency**:
   - **Issue**: Cycle between `JwtAuthenticationFilter`, `UserService`, and `SecurityConfig`.
   - **Fix**: Removed `UserDetailsService` from `SecurityConfig` constructor, injected it into `securityFilterChain` method.
   - **Files Updated**:
     - `SecurityConfig.java`: Removed `UserDetailsService` dependency.
     - `JwtAuthenticationFilter.java`: Used `jakarta.servlet` imports.
     - `application.properties`: Updated to `MySQLDialect`.
2. **403 Forbidden on `/api/auth/login`**:
   - **Issue**: Likely due to CSRF protection or CORS restrictions.
   - **Fix**:
     - Disabled CSRF in `SecurityConfig.java`.
     - Added CORS configuration to allow Postman requests.
     - Added debug logging in `AuthController.java` and `application.properties`.
   - **Files Updated**:
     - `SecurityConfig.java`: Added CORS and confirmed CSRF disablement.
     - `AuthController.java`: Added logging for debugging.
     - `application.properties`: Enabled Spring Security debug logging.
3. **MySQL Dialect Deprecation**:
   - **Issue**: Warning about `MySQL8Dialect` deprecation.
   - **Fix**: Updated `application.properties` to use `org.hibernate.dialect.MySQLDialect`.

## Contributing
- Fork the repository and create a feature branch.
- Submit pull requests with clear descriptions of changes.
- Ensure code follows project conventions and includes tests.

## License
This project is licensed under the MIT License.