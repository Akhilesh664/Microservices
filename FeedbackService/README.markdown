# Feedback and Student Management System

This is a Spring Boot application that combines two functionalities:
1. **Feedback Service**: A REST API microservice for collecting and displaying user feedback/reviews for products or orders.
2. **Student Management System**: A Thymeleaf-based web application for managing student records (CRUD operations).

The project uses MySQL for persistence, Spring Data JPA for database operations, and Thymeleaf for rendering web views. It follows a layered architecture with DTOs, services, and repositories for clean code organization.

## Table of Contents
- [Project Structure](#project-structure)
- [Application Flow](#application-flow)
- [Necessary Knowledge](#necessary-knowledge)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Endpoints](#endpoints)
- [Testing with Postman](#testing-with-postman)
- [Testing Web UI](#testing-web-ui)
- [Important Notes](#important-notes)
- [Troubleshooting](#troubleshooting)

## Project Structure
```
feedback-student-system/
├── src/main/java/
│   ├── com/microservice/FeedbackService/
│   │   ├── Controller/         # REST controllers for Feedback Service
│   │   │   ├── FeedbackController.java
│   │   │   └── RootController.java  # Handles root URL
│   │   ├── Dto/               # Data Transfer Objects for Feedback
│   │   │   ├── FeedbackRequestDTO.java
│   │   │   └── FeedbackResponseDTO.java
│   │   ├── Entity/            # JPA entities for Feedback
│   │   │   └── Feedback.java
│   │   ├── Repository/        # JPA repositories for Feedback
│   │   │   └── FeedbackRepository.java
│   │   ├── Service/           # Service interfaces for Feedback
│   │   │   └── FeedbackService.java
│   │   ├── Service/Impl/      # Service implementations for Feedback
│   │   │   └── FeedbackServiceImpl.java
│   ├── com/example/StudentManagementSystem/
│   │   ├── Controller/         # Web controllers for Student Management
│   │   │   └── StudentController.java
│   │   ├── Entity/            # JPA entities for Student
│   │   │   └── Student.java
│   │   ├── Service/           # Service interfaces for Student
│   │   │   └── StudentService.java
│   │   └── StudentServiceImpl.java
│   │       ├── Repository/        # JPA repositories for Student
│   │           └── StudentRepository.java
│   └── Application.java       # Main application (shared)
├── src/main/resources/
│   ├── templates/              # Thymeleaf templates for Student Management
│   │   ├── students.html
│   │   ├── create_student.html
│   │   └── edit_student.html
│   └── application.properties  # Configuration for MySQL, port, etc.
├── pom.xml                    # Maven dependencies
└── README.md                  # Project documentation
```

### Key Files
- **Feedback Service**:
  - `FeedbackController.java`: Handles REST API endpoints (`/api/feedback`).
  - `FeedbackRequestDTO.java`, `FeedbackResponseDTO.java`: DTOs for request/response.
  - `Feedback.java`: JPA entity for the `feedback` table.
  - `FeedbackService.java`, `FeedbackServiceImpl.java`: Business logic for feedback operations.
  - `FeedbackRepository.java`: JPA repository for database access.
- **Student Management System**:
  - `StudentController.java`: Handles web endpoints (`/students`).
  - `Student.java`: JPA entity for the `student` table.
  - `StudentService.java`, `StudentServiceImpl.java`: Business logic for student operations.
  - `StudentRepository.java`: JPA repository for database access.
- **Shared**:
  - `RootController.java`: Handles root URL (`/`) to redirect to `/students`.
  - `Application.java`: Spring Boot main class.
  - `application.properties`: Configures MySQL, port (8081), and DevTools.
  - `pom.xml`: Includes dependencies for Spring Boot, MySQL, JPA, Thymeleaf, and DevTools.

## Application Flow

### Feedback Service (REST API)
1. **User Submits Feedback**:
   - Sends a `POST /api/feedback` request with `productId`, `rating` (1-5), `comment`, and `userId`.
   - `FeedbackController` validates the `FeedbackRequestDTO` and calls `FeedbackService`.
   - `FeedbackService` maps DTO to `Feedback` entity and saves via `FeedbackRepository`.
   - Returns a `FeedbackResponseDTO` with the saved feedback.
2. **User Retrieves Feedback**:
   - Sends a `GET /api/feedback/product/{productId}` request.
   - `FeedbackController` calls `FeedbackService` to fetch feedback by `productId`.
   - `FeedbackService` queries `FeedbackRepository` and maps entities to `FeedbackResponseDTOs`.
   - Returns a list of feedback.

### Student Management System (Web UI)
1. **List Students**:
   - User visits `/students` (or `/` which redirects to `/students`).
   - `StudentController` calls `StudentService` to fetch all students.
   - Renders `students.html` with the student list.
2. **Create Student**:
   - User visits `/students/new`.
   - `StudentController` provides a blank `Student` object to `create_student.html`.
   - User submits form (`POST /students`).
   - `StudentController` saves via `StudentService` and redirects to `/students`.
3. **Edit/Delete Student**:
   - User visits `/students/edit/{id}` or `/students/{id}` (delete).
   - `StudentController` fetches/updates/deletes via `StudentService` and redirects to `/students`.

## Necessary Knowledge
- **Java 21**: Core programming language.
- **Spring Boot 3.3.5**: Framework for building REST APIs and web applications.
- **Spring Data JPA**: For database operations with MySQL.
- **MySQL**: Relational database for storing feedback and student data.
- **Thymeleaf**: Templating engine for rendering web views.
- **Maven**: Build tool for dependency management.
- **REST APIs**: Understanding HTTP methods (GET, POST) and JSON payloads.
- **Postman**: For testing REST API endpoints.
- **HTML/CSS**: Basic knowledge for Thymeleaf templates.
- **Spring DevTools**: For automatic restarts during development.
- **Validation**: Using Jakarta Bean Validation for DTOs.

## Prerequisites
- **Java 21 JDK**: Installed and configured (`JAVA_HOME` set).
- **Maven**: Installed for building the project.
- **MySQL**: Running locally or in a container (e.g., Docker).
- **Postman**: For testing REST APIs.
- **Web Browser**: For accessing the Student Management System UI.
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code (optional, for development).

## Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd feedback-student-system
   ```

2. **Set Up MySQL**:
   - Install MySQL or run via Docker:
     ```bash
     docker run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=your_password -d mysql:latest
     ```
   - Create a database named `feedback_db`:
     ```sql
     CREATE DATABASE feedback_db;
     ```
   - Update `src/main/resources/application.properties` with your MySQL credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/feedback_db?createDatabaseIfNotExist=true
     spring.datasource.username=root
     spring.datasource.password=your_password
     ```

3. **Build the Project**:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```
   - The application starts on `http://localhost:8081` (port 8081).

5. **Verify Setup**:
   - Access `http://localhost:8081` (redirects to `http://localhost:8081/students`).
   - Test Feedback API with Postman (see below).

## Endpoints

### Feedback Service (REST API)
| Method | Endpoint                        | Description                          | Request Body Example                                                                 |
|--------|---------------------------------|--------------------------------------|-------------------------------------------------------------------------------------|
| POST   | `/api/feedback`                 | Submit feedback for a product        | `{"productId": 1, "rating": 4, "comment": "Great product!", "userId": "user123"}`   |
| GET    | `/api/feedback/product/{productId}` | Retrieve feedback for a product  | None                                                                                |

### Student Management System (Web UI)
| Method | Endpoint                     | Description                          |
|--------|------------------------------|--------------------------------------|
| GET    | `/`                          | Redirects to `/students`             |
| GET    | `/students`                  | List all students                    |
| GET    | `/students/new`              | Show form to create a student        |
| POST   | `/students`                  | Save a new student                   |
| GET    | `/students/edit/{id}`        | Show form to edit a student          |
| POST   | `/students/{id}`             | Update a student                     |
| GET    | `/students/{id}`             | Delete a student                     |

## Testing with Postman
1. **Create Feedback**:
   - **Method**: POST
   - **URL**: `http://localhost:8081/api/feedback`
   - **Headers**: `Content-Type: application/json`
   - **Body**:
     ```json
     {
         "productId": 1,
         "rating": 4,
         "comment": "Great product, fast delivery!",
         "userId": "user123"
     }
     ```
   - **Expected Response**: `201 Created` with saved feedback.

2. **Get Feedback**:
   - **Method**: GET
   - **URL**: `http://localhost:8081/api/feedback/product/1`
   - **Expected Response**: `200 OK` with a list of feedback.

3. **Validation Errors**:
   - Try `rating: 6` or missing `userId` to test validation (`400 Bad Request`).

## Testing Web UI
1. **Access Student List**:
   - Open `http://localhost:8081` (redirects to `/students`).
   - Verify the student list displays (requires `students.html`).

2. **Create Student**:
   - Visit `http://localhost:8081/students/new`.
   - Fill out the form and submit.
   - Verify redirection to `/students` with the new student.

3. **Edit/Delete Student**:
   - Click edit/delete links on the student list page.
   - Verify updates/deletions reflect in the UI.

## Important Notes
- **Port**: The application runs on port `8081` (configurable in `application.properties`).
- **Database**: Uses a single MySQL database (`feedback_db`) with tables `feedback` and `student`. Ensure both tables are created (`spring.jpa.hibernate.ddl-auto=update` handles this).
- **DevTools**: Enabled for automatic restarts during development (`spring-boot-devtools` dependency).
- **Thymeleaf Templates**: Ensure `students.html`, `create_student.html`, and `edit_student.html` exist in `src/main/resources/templates/`.
- **Feedback Service Assumptions**:
  - `productId` is assumed valid (no integration with a product service).
  - `userId` is provided externally (no authentication).
- **Student Management System**:
  - Requires Thymeleaf and web dependencies (`spring-boot-starter-thymeleaf`).
  - Assumes `Student` entity has fields `id`, `firstName`, `lastName`, `email`.
- **Separation**: For production, consider splitting Feedback Service and Student Management System into separate applications to avoid package conflicts.

## Troubleshooting
- **Whitelabel Error Page**:
  - Ensure `RootController` or `StudentController` handles `/`.
  - Verify Thymeleaf templates exist.
  - Check logs for mapping errors:
    ```bash
    tail -f target/spring-boot.log
    ```
- **Database Connection Issues**:
  - Confirm MySQL is running and credentials are correct in `application.properties`.
  - Check for table creation:
    ```sql
    USE feedback_db;
    SHOW TABLES;
    ```
- **Port Conflicts**:
  - If `8081` is in use, change `server.port` in `application.properties` or kill the process:
    ```bash
    netstat -an | find "8081"
    taskkill /PID <pid> /F  # Windows
    ```
- **Feedback API Errors**:
  - Validate JSON payloads in Postman.
  - Check for validation errors (`400 Bad Request`).
- **Student UI Errors**:
  - Ensure `StudentService` and `StudentRepository` are implemented.
  - Verify Thymeleaf templates are correctly formatted.