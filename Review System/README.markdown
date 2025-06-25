# Sentiment-Based Product Review System

This is a beginner-friendly microservices-based application that allows users to submit product reviews, analyze their sentiment, and display them. The system uses a Java-based tech stack with a simple lexicon-based sentiment analysis approach, requiring no external API keys or cloud services, making it completely free and easy to set up.

## Table of Contents
- [Project Overview](#project-overview)
- [Project Structure](#project-structure)
- [System Flow](#system-flow)
- [Necessary Knowledge](#necessary-knowledge)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Running the Application](#running-the-application)
- [Testing the Application](#testing-the-application)
- [Extending the Project](#extending-the-project)
- [Limitations](#limitations)
- [Troubleshooting](#troubleshooting)

## Project Overview
The Sentiment-Based Product Review System consists of two microservices and a frontend:
- **Review Service**: A Spring Boot application that handles CRUD operations for product reviews, storing them in a MySQL database.
- **Sentiment Service**: A Spring Boot application that analyzes the sentiment of reviews using a lexicon-based approach (word list with positive/negative scores) and updates the database.
- **Frontend**: A simple HTML/CSS/JavaScript interface to submit reviews and display them with their sentiments.
- **Database**: A MySQL database to store reviews and their sentiments.

The system is designed for beginners, avoiding complex dependencies, external APIs, or cloud costs. The sentiment analysis is performed locally using a simple word list, making it free and keyless.

## Project Structure
```
review-system/
├── review-service/                  # Spring Boot service for review CRUD operations
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/reviewservice/
│   │   │   │   ├── ReviewServiceApplication.java  # Main application
│   │   │   │   ├── Review.java                   # Review entity
│   │   │   │   ├── ReviewRepository.java         # JPA repository
│   │   │   │   ├── ReviewController.java         # REST API endpoints
│   │   │   ├── resources/
│   │   │   │   ├── application.properties        # Database configuration
│   │   ├── pom.xml                               # Maven dependencies
├── sentiment-service/               # Spring Boot service for sentiment analysis
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/sentimentservice/
│   │   │   │   ├── SentimentServiceApplication.java  # Main application
│   │   │   │   ├── SentimentController.java         # REST API for sentiment analysis
│   │   │   │   ├── SentimentAnalyzer.java           # Lexicon-based sentiment logic
│   │   │   ├── resources/
│   │   │   │   ├── application.properties          # Service configuration
│   │   │   │   ├── sentiment-lexicon.txt           # Word list for sentiment analysis
│   │   ├── pom.xml                                 # Maven dependencies
├── frontend/                       # HTML/CSS/JS frontend
│   ├── index.html                 # Main page with form and review table
│   ├── styles.css                 # Styling for the frontend
│   ├── script.js                  # JavaScript for API calls
├── database/
│   ├── schema.sql                 # MySQL database schema
├── README.md                      # This file
```

## System Flow
1. **Submit Review**:
   - User submits a review (product ID and review text) via the frontend (`http://localhost:8000`).
   - Frontend sends a POST request to Review Service (`http://localhost:8080/api/reviews`).
   - Review Service saves the review in MySQL with `sentiment="PENDING"` and triggers Sentiment Service (`http://localhost:8081/api/sentiment/analyze/{reviewId}`).

2. **Sentiment Analysis**:
   - Sentiment Service fetches the review text from Review Service.
   - It analyzes the text using a lexicon-based approach (e.g., `great=1`, `bad=-1`), summing word scores to determine sentiment (`POSITIVE`, `NEUTRAL`, `NEGATIVE`).
   - Sentiment Service updates the review’s sentiment via a PUT request to Review Service.

3. **Display Reviews**:
   - Frontend sends a GET request to Review Service (`http://localhost:8080/api/reviews`) to fetch and display all reviews with their sentiments in a table.

**Example**:
- Review: “This product is great!” → Score: +1 (due to `great=1`) → Sentiment: `POSITIVE`
- Review: “This product is terrible.” → Score: -1 (due to `terrible=-1`) → Sentiment: `NEGATIVE`
- Review: “This product is okay.” → Score: 0 (no matching words) → Sentiment: `NEUTRAL`

## Necessary Knowledge
To understand and run this project, you should be familiar with:
- **Basic Java**: Understanding classes, methods, and Spring Boot basics (e.g., `@RestController`, `@Autowired`).
- **Spring Boot**: Familiarity with REST APIs, dependency injection, and Maven.
- **MySQL**: Basic database setup and SQL queries (e.g., creating a database and tables).
- **HTML/CSS/JavaScript**: Basic web development for the frontend (e.g., forms, Fetch API).
- **Command Line**: Running commands like `mvn spring-boot:run` and serving static files with `python3 -m http.server`.
- **REST APIs**: Understanding HTTP methods (GET, POST, PUT) and JSON.

No advanced AI or NLP knowledge is required, as the sentiment analysis uses a simple word-list approach.

## Prerequisites
- **Java 17+**: For running Spring Boot services.
- **Maven**: For building and managing Java dependencies.
- **MySQL 8.0+**: For storing reviews.
- **Python 3.8+**: For serving the frontend (optional, used for `python3 -m http.server`).
- **Web Browser**: For accessing the frontend (e.g., Chrome, Firefox).
- **Text Editor**: For editing code (e.g., VS Code, IntelliJ IDEA).

## Setup Instructions
1. **Clone or Set Up the Project**:
   - Create the project structure as shown above.
   - Copy the provided files (from previous responses) into their respective directories:
     - Review Service: `ReviewServiceApplication.java`, `Review.java`, `ReviewRepository.java`, `ReviewController.java`, `application.properties`, `pom.xml`.
     - Sentiment Service: `SentimentServiceApplication.java`, `SentimentController.java`, `SentimentAnalyzer.java`, `application.properties`, `sentiment-lexicon.txt`, `pom.xml`.
     - Frontend: `index.html`, `styles.css`, `script.js`.
     - Database: `schema.sql`.

2. **Set Up MySQL**:
   - Install MySQL (https://dev.mysql.com/downloads/) and start the server.
   - Create the database and table:
     ```bash
     mysql -u root -p
     ```
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
   - Update `review-service/src/main/resources/application.properties` with your MySQL credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/review_system
     spring.datasource.username=root
     spring.datasource.password=your_mysql_password
     spring.jpa.hibernate.ddl-auto=update
     server.port=8080
     ```

3. **Verify Sentiment Service Configuration**:
   - Ensure `sentiment-lexicon.txt` is in `sentiment-service/src/main/resources/`.
   - The file contains word-score pairs (e.g., `great=1`, `bad=-1`). Add more words to improve accuracy (e.g., from AFINN: https://github.com/fnielsen/afinn/blob/master/afinn/data/AFINN-111.txt).
   - No API keys are needed, as sentiment analysis is local.

## Running the Application
1. **Start Review Service**:
   ```bash
   cd review-service
   mvn spring-boot:run
   ```
   - Runs on `http://localhost:8080`.

2. **Start Sentiment Service**:
   ```bash
   cd sentiment-service
   mvn spring-boot:run
   ```
   - Runs on `http://localhost:8081`.

3. **Serve the Frontend**:
   ```bash
   cd frontend
   python3 -m http.server 8000
   ```
   - Access the frontend at `http://localhost:8000`.

4. **Access the Application**:
   - Open `http://localhost:8000` in a browser.
   - Enter a product ID (e.g., “123”) and review text (e.g., “This product is great!”).
   - Submit the review and see it in the table with its sentiment (e.g., `POSITIVE`).

## Testing the Application
1. **Test via Frontend**:
   - Go to `http://localhost:8000`.
   - Submit a review and verify the sentiment updates in the table.

2. **Test APIs Directly**:
   - **Submit a review**:
     ```bash
     curl -X POST http://localhost:8080/api/reviews -H "Content-Type: application/json" -d '{"productId":"123","reviewText":"This product is great!"}'
     ```
   - **Get all reviews**:
     ```bash
     curl http://localhost:8080/api/reviews
     ```
   - **Trigger sentiment analysis** (for a review with ID 1):
     ```bash
     curl -X POST http://localhost:8081/api/sentiment/analyze/1
     ```

3. **Expected Output**:
   - Review: “This product is great!” → Sentiment: `POSITIVE`
   - Review: “This product is terrible.” → Sentiment: `NEGATIVE`
   - Review: “This product is okay.” → Sentiment: `NEUTRAL`

## Extending the Project
- **Improve Sentiment Analysis**:
  - Expand `sentiment-lexicon.txt` with more words from free lexicons like AFINN.
  - Add negation handling (e.g., “not good” → negative) in `SentimentAnalyzer.java`.
- **Enhance Frontend**:
  - Add loading indicators or error messages in `script.js`.
  - Style the table with additional CSS in `styles.css`.
- **Add Features**:
  - Filter reviews by product ID or sentiment in the Review Service.
  - Add user authentication for submitting reviews.
- **Use Advanced NLP**:
  - Switch to Stanford CoreNLP (https://stanfordnlp.github.io/CoreNLP/) for more accurate sentiment analysis (requires model downloads and additional setup).

## Limitations
- **Sentiment Accuracy**: The lexicon-based approach is simple and may miss context (e.g., sarcasm, complex phrases). For better accuracy, consider Stanford CoreNLP or cloud APIs (if accessible).
- **Scalability**: The system is designed for learning, not production. For high traffic, add load balancing or caching.
- **Lexicon Size**: The provided `sentiment-lexicon.txt` is minimal. Expand it for better coverage.

## Troubleshooting
- **Frontend Can’t Connect to Review Service**:
  - Add CORS support to `ReviewController.java`:
    ```java
    import org.springframework.web.bind.annotation.CrossOrigin;

    @RestController
    @RequestMapping("/api/reviews")
    @CrossOrigin(origins = "http://localhost:8000")
    public class ReviewController {
        // ... rest of the code ...
    }
    ```
- **Sentiment Not Updating**:
  - Ensure `sentiment-lexicon.txt` is in `sentiment-service/src/main/resources/`.
  - Check console logs for file-loading errors in `SentimentAnalyzer.java`.
  - Verify the review ID exists when calling `http://localhost:8081/api/sentiment/analyze/{id}`.
- **MySQL Errors**:
  - Confirm MySQL is running and credentials in `application.properties` are correct.
  - Run `schema.sql` to set up the database.
- **Java Errors**:
  - Ensure Java 17+ and Maven are installed.
  - Run `mvn clean install` in both `review-service` and `sentiment-service` to resolve dependency issues.

For further help, share error messages or specific issues, and I’ll provide detailed guidance.

---

**Last Updated**: June 25, 2025