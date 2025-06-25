# Microservices Portfolio 🚀

Welcome to my Microservices Portfolio! This repository showcases a collection of Spring Boot-based microservices projects designed for various functionalities, including AI-powered text summarization, hotel management, sentiment-based review systems, and more. Each project is built with modern Java technologies and follows a microservices architecture for scalability and modularity. Below, you'll find detailed descriptions of the prioritized projects (Text Summarizer, Hotel Management, and Review System) followed by other notable microservices.

---

## Table of Contents
- [Text Summarizer Microservice 📝](#text-summarizer-microservice)
- [Hotel Management Microservices 🏨](#hotel-management-microservices)
- [Sentiment-Based Product Review System 🌟](#sentiment-based-product-review-system)
- [Other Microservices](#other-microservices)
  - [Auth Service App](#auth-service-app)
  - [Feedback Service](#feedback-service)
  - [Product Catalog](#product-catalog)
- [Contributing 🤝](#contributing)
- [License 📜](#license)

---

## Text Summarizer Microservice 📝

This Spring Boot-based microservice leverages **ONNX models** for text summarization, providing a REST API and a Thymeleaf-based web interface to summarize input text using an encoder-decoder architecture. The application uses the **ONNX Runtime** for model inference, with placeholder preprocessing and postprocessing pipelines for tokenization.

### Key Features
- Summarize text via REST API (`/api/summarize`) or web UI.
- Uses `encoder_model.onnx` and `decoder_model.onnx` for AI-driven summarization.
- Built with **Spring Boot**, **Thymeleaf**, and **ONNX Runtime**.

### Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone <your-repository-url>
   cd TextSummarizer
   ```
2. **Place ONNX Models**:
   - Download `encoder_model.onnx` and `decoder_model.onnx` from [Google Drive Folder](<your-drive-folder-link>).
   - Place them in `src/main/resources/model/`.
3. **Install Dependencies**:
   - Ensure `pom.xml` includes:
     ```xml
     <dependencies>
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-web</artifactId>
             <version>3.4.5</version>
         </dependency>
         <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-thymeleaf</artifactId>
             <version>3.4.5</version>
         </dependency>
         <dependency>
             <groupId>com.microsoft.onnxruntime</groupId>
             <artifactId>onnxruntime</artifactId>
             <version>1.17.0</version>
         </dependency>
         <dependency>
             <groupId>ai.djl.huggingface</groupId>
             <artifactId>tokenizers</artifactId>
             <version>0.32.0</version>
         </dependency>
     </dependencies>
     ```
4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```
5. **Access**:
   - REST API: `http://localhost:8080/api/summarize`
   - Web UI: `http://localhost:8080/`

## Model Download

The ONNX model is too large to be included in the repo.

➡️ [Download model.onnx from Google Drive]: [https://drive.google.com/drive/folders/1USD0UmWzLojfP1NG0jOpYGXGFxa1dgWa?usp=sharing]

### Notes
- Replace placeholder tokenization with **Hugging Face’s tokenizer** for production use.
- Use **Netron** (https://netron.app/) to inspect model inputs/outputs.

---

## Hotel Management Microservices 🏨

This project is a robust microservices-based system for managing hotel operations, including room bookings, hotel details, and user ratings. It uses **Spring Cloud** for service discovery and configuration, with **OAuth 2.0** for secure authentication.

### Key Features
- **User Service**: User registration, authentication, room bookings, and wallet transactions.
- **Hotel Service**: Add hotels/rooms, retrieve details, and list available/booked rooms.
- **Booking Service**: Manage room bookings and retrieve booking details.
- **Rating Service**: Add and retrieve hotel ratings/reviews.
- **Service Registry & API Gateway**: Centralized service discovery and routing.

### Tech Stack
- **Java**, **Spring Boot**, **Spring Cloud** (Eureka, Config, Gateway)
- **Spring Security**, **OAuth 2.0**, **Spring Data JPA**, **Spring Data MongoDB**
- **MySQL**, **MongoDB**, **Thymeleaf**, **Okta**

### Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone <your-repository-url>
   cd Hotel-Management-Microservices
   ```
2. **Configure Services**:
   - Update properties in `application.properties` for each microservice.
3. **Run Services**:
   ```bash
   mvn spring-boot:run
   ```
4. **Check Service Status**:
   - Eureka Dashboard: `http://localhost:8761`
   - API Gateway: `http://localhost:8086`

### API Endpoints
- **User Service**: `http://localhost:8086/users/register`, `http://localhost:8086/users/addBooking`
- **Hotel Service**: `http://localhost:8086/hotels/all`, `http://localhost:8086/hotels/{hotelId}`
- **Rating Service**: `http://localhost:8086/ratings/add`, `http://localhost:8086/ratings/hotel/{hotelId}`

---

## Sentiment-Based Product Review System 🌟

This beginner-friendly microservices application allows users to submit product reviews and analyze their sentiment using a **lexicon-based AI approach**. It’s completely free, requiring no external APIs or cloud services.

### Key Features
- **Review Service**: CRUD operations for reviews, stored in **MySQL**.
- **Sentiment Service**: Analyzes review sentiment (POSITIVE, NEUTRAL, NEGATIVE) using a local word list.
- **Frontend**: Simple HTML/CSS/JS interface for submitting and viewing reviews.

### Tech Stack
- **Java**, **Spring Boot**, **Spring Data JPA**
- **MySQL**, **HTML/CSS/JavaScript**

### Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone <your-repository-url>
   cd review-system
   ```
2. **Set Up MySQL**:
   ```sql
   CREATE DATABASE review_system;
   USE review_system;
   CREATE TABLE reviews (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       product_id VARCHAR(50) NOT NULL,
       review_text TEXT NOT NULL,
       sentiment VARCHAR(20) DEFAULT 'PENDING',
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );
   ```
3. **Run Services**:
   - Review Service: `cd review-service && mvn spring-boot:run`
   - Sentiment Service: `cd sentiment-service && mvn spring-boot:run`
   - Frontend: `cd frontend && python3 -m http.server 8000`
4. **Access**:
   - Frontend: `http://localhost:8000`
   - Review API: `http://localhost:8080/api/reviews`
   - Sentiment API: `http://localhost:8081/api/sentiment/analyze/{reviewId}`

---

## Other Microservices

### Auth Service App 🔒
- **Description**: A Spring Boot microservice for user authentication and authorization using **OAuth 2.0** and **JWT**.
- **Features**: User login, token generation, and role-based access control.
- **Tech Stack**: Spring Boot, Spring Security, Okta.
- **API Endpoints**: `http://localhost:8080/auth/login`, `http://localhost:8080/auth/validate`.

---

### Feedback Service 📬
- **Description**: A microservice for collecting and managing user feedback.
- **Features**: Submit feedback, retrieve feedback by user or product, and basic analytics.
- **Tech Stack**: Spring Boot, Spring Data JPA, MySQL.
- **API Endpoints**: `http://localhost:8082/feedback/submit`, `http://localhost:8082/feedback/all`.

---

### Product Catalog 🛍️
- **Description**: A microservice to manage product listings and details.
- **Features**: Add, update, delete, and retrieve product information.
- **Tech Stack**: Spring Boot, Spring Data MongoDB, MongoDB.
- **API Endpoints**: `http://localhost:8083/products/all`, `http://localhost:8083/products/{productId}`.

---

## Contributing 🤝
Contributions are welcome! Please create an issue or submit a pull request to contribute to any of the microservices in this portfolio.

## License 📜
This project is licensed under the MIT License.

**Last Updated**: June 25, 2025