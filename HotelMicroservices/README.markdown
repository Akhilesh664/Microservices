# Hotel Management Microservices

Welcome to the Hotel Management Microservices project. This application is designed to manage various aspects of a hotel, including room bookings, hotel management, and user ratings through a microservices architecture.

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
  - [Installation](#installation)
  - [Configuration](#configuration)
  - [Usage](#usage)
- [Checking Service Status](#checking-service-status)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)

## Features

This application provides the following features:

### User Service
- User registration and authentication.
- Book hotel rooms.
- Cancel bookings.
- Wallet system to track user transactions.

### Hotel Service
- Add hotels with details.
- Add rooms to hotels.
- Retrieve hotel details by ID or name.
- List all available or booked rooms in a hotel.

### Booking Service
- Book rooms in hotels.
- Retrieve booking details by booking ID.
- List all bookings.

### Rating Service
- Add ratings and reviews for hotels.
- Retrieve all ratings or filter by user ID or hotel ID.

### Service Registry
- Register and discover microservices.

### Configuration Server
- Manage centralized configurations for microservices.

### API Gateway
- Centralized gateway for accessing microservices.

## Tech Stack

- **Java**
- **Spring Boot**
- **Spring Cloud**
  - Spring Cloud Eureka
  - Spring Cloud Config
  - Spring Cloud Gateway
- **Spring Security** with **OAuth 2.0**
- **Spring Data JPA**
- **Spring Web**
- **Spring Data MongoDB**
- **Spring Data REST**
- **Netflix Eureka**
- **Thymeleaf**
- **MySQL**
- **MongoDB**
- **Okta**
- **Git**

## Getting Started

### Installation
1. Clone this repository:
   ```bash
   git clone https://github.com/Akhilesh664/Hotel-Management-Microservices.git
   ```
2. Navigate to the project directory:
   ```bash
   cd Hotel-Management-Microservices
   ```

### Configuration
- Configure the properties of each microservice in their respective configuration files based on your environment and requirements.

### Usage
- Run each microservice individually to start the Hotel Management system.

## Checking Service Status

### Eureka Server
- **Eureka Dashboard**: `http://localhost:8761`
  - Monitor the status of all registered microservices, including their availability and instances.

### Registered Instances
- **API-GATEWAY**: UP at `192.168.1.4:8086`
- **CONFIG-SERVER**: UP at `192.168.1.4:8085`
- **HOTELS-SERVICE**: UP at `192.168.1.4:8082`
- **RATING-SERVICE**: UP at `192.168.1.4:8083`
- **USERS-SERVICE**: UP at `192.168.1.4:8081`

### API Gateway Default URL
- `http://localhost:8086`

## API Endpoints

### User Service Routes
- **User login**: `http://localhost:8086/auth/login`
  - Obtain an access token for authenticated requests.
- **Fetch all users**: `http://localhost:8086/users/all`
- **User registration**: `http://localhost:8086/users/register`
- **User login**: `http://localhost:8086/users/login`
- **Book a hotel room**: `http://localhost:8086/users/addBooking`
- **Complete a booking**: `http://localhost:8086/users/completeBooking/{bookingId}`
- **Cancel a booking**: `http://localhost:8086/users/cancelBooking/{bookingId}`
- **Add wallet balance**: `http://localhost:8086/users/wallet/addMoney/{email}?amount=00.0`
- **View wallet balance**: `http://localhost:8086/users/wallet/getBalance/{email}`
- **View wallet transactions**: `http://localhost:8086/users/wallet/getTransactions/{email}`

### Hotel Service Routes
- **Fetch all hotels**: `http://localhost:8086/hotels/all`
- **Fetch hotel by ID**: `http://localhost:8086/hotels/{hotelId}`
- **Add a hotel**: `http://localhost:8086/hotels/add`
- **Add a room to a hotel**: `http://localhost:8086/hotels/{hotelId}/rooms/add`
- **Book a room in a hotel**: `http://localhost:8086/hotels/{hotelId}/bookings/add`
- **Get all bookings in a hotel**: `http://localhost:8086/hotels/{hotelId}/bookings/all`

### Rating Service Routes
- **Fetch all ratings**: `http://localhost:8086/ratings/all`
- **Add a rating**: `http://localhost:8086/ratings/add`
- **Fetch ratings by user ID**: `http://localhost:8086/ratings/user/{userId}`
- **Fetch ratings by hotel ID**: `http://localhost:8086/ratings/hotel/{hotelId}`

## Contributing

Contributions are welcome! Please create an issue or submit a pull request to contribute to this project.

## License

This project is licensed under the MIT License.