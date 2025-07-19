#  Ride Matching Service (Spring Boot)

A backend system for efficiently matching ride requests with the nearest available drivers. This service is designed with in-memory storage and thread safety, and supports all core ride-hailing features.

---

## 📦 Features

- Register and update drivers with location
- Request a ride and auto-assign nearest available driver
- Mark rides as completed
- Query nearest available drivers
- Thread-safe and concurrent-friendly
- RESTful API with JUnit integration tests
- No database – fully in-memory

---

## ▶️ How to Run

Make sure you're in the project root directory, then run:

```bash
./mvnw spring-boot:run
```

Once running, the service is available at:  
📍 `http://localhost:8080`

---

## 🧪 API Endpoints & Examples

### 1. Register Driver  
**POST** `/api/drivers`

**Request:**
```json
{
  "name": "Alice",
  "x": 10.0,
  "y": 5.0
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Alice",
  "location": { "x": 10.0, "y": 5.0 },
  "available": true
}
```

---

### 2. Request a Ride  
**POST** `/api/rides`

**Request:**
```json
{
  "x": 10.1,
  "y": 5.1
}
```

**Response:**
```json
{
  "rideId": 1,
  "driver": {
    "id": 1,
    "name": "Alice",
    "location": { "x": 10.0, "y": 5.0 },
    "available": false
  },
  "pickupLocation": {
    "x": 10.1,
    "y": 5.1
  }
}
```

---

### 3. Complete a Ride  
**POST** `/api/rides/{rideId}/complete`

**Example:**
```http
POST /api/rides/1/complete
```

**Response:**
```http
HTTP 200 OK
```

---

### 4. Get All Rides  
**GET** `/api/ride/all`

**Response:**
```json
[
  {
    "rideId": 1,
    "driver": {
      "id": 1,
      "name": "Alice",
      "location": { "x": 10.0, "y": 5.0 },
      "available": false
    },
    "pickupLocation": { "x": 10.1, "y": 5.1 }
  }
]
```

---

### 5. Get Nearest Available Drivers  
**GET** `/api/drivers/available?x=10&y=5&limit=3`

**Response:**
```json
[
  {
    "id": 1,
    "name": "Alice",
    "location": { "x": 10.0, "y": 5.0 },
    "available": true
  }
]
```

---

## 📬 Postman Collection

Import the following collection into Postman:  
📄 `RideMatching-API.postman_collection.json`

---

## 📁 Project Structure

```
src
├── main
│   └── java
│       └── com.example.ridematching
│           ├── controller
│           ├── service
│           ├── model
│           ├── dto
│           └── repository
└── test
    └── java
        └── com.example.ridematching.controller
            └── DriverControllerIntegrationTest.java
```

---

## 🧪 Run All Tests

```bash
./mvnw test
```

---

## 🛠 Technologies Used

- Java 17+
- Spring Boot 3.x
- Maven
- JUnit 5
- MockMvc
- In-memory repositories
- Postman

---


