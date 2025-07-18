🚕 Ride Matching Service
A simple backend ride-matching service implemented in Spring Boot, using in-memory storage and
Euclidean distance to match riders to the nearest available drivers.
📌 Features
•
•
•
•
•
•
Register a driver with location and availability
Request a ride (nearest driver is allocated)
Complete a ride (driver becomes available again)
Query the nearest available drivers
In-memory data storage using ConcurrentHashMap
Thread-safe design with unit and integration tests
⚙️ Tech Stack
•
•
•
•
•
•
Java 17+
Spring Boot 3.x
Maven
JUnit 5
MockMvc (integration tests)
Postman (API testing)
🚀 How to Run the Project
✅ Option 1: Run from IDE (IntelliJ / Eclipse)
1.
2.
Open the project in your IDE
Run the main class:
com.example.ridematching.RideMatchingApplication
✅ Option 2: Run from Terminal
./mvnw spring-boot:run
Or build and run:
1
./mvnw clean install
java -jar target/ridematching-0.0.1-SNAPSHOT.jar
App will start at: http://localhost:8080
📬 API Endpoints
Method Endpoint Description
POST /api/drivers Register a new driver
GET /api/drivers/available Get nearest X available drivers
POST /api/rides Request a ride
POST /api/rides/{rideId}/complete Complete a ride
GET /api/ride/all Get all rides
🧪 API Testing
✅ With Postman
Use the included Postman collection:
📄 RideMatching-API.postman_collection.json
1.
2.
3.
Open Postman → Import
Select the JSON file
Call any of the available endpoints
✅ With Curl
# Register a driver
curl -X POST http://localhost:8080/api/drivers \
-H "Content-Type: application/json" \
-d '{"name": "Alice", "x": 10.0, "y": 5.0}'
# Request a ride
curl -X POST http://localhost:8080/api/rides \
-H "Content-Type: application/json" \
-d '{"x": 10.1, "y": 5.2}'
# Complete a ride (replace :id)
2
curl -X POST http://localhost:8080/api/rides/1/complete
# Get all rides
curl http://localhost:8080/api/ride/all
# Get nearest available drivers
curl "http://localhost:8080/api/drivers/available?x=10&y=5&limit=3"
🧪 Running Tests
Run all unit and integration tests:
./mvnw test
Includes:
•
•
✅ DriverControllerIntegrationTest (covers all endpoints)
✅ RideServiceTest and more (if added)
🧩 Project Structure
src/
├── main/java/com/example/ridematching
│ ├── controller # REST APIs
│ ├── dto # Request/response models
│ ├── model # Core domain classes (Driver, Ride, Location)
│ ├── repository # In-memory storage
│ ├── service # Business logic
│ └── RideMatchingApplication.java
├── test/java/com/example/ridematching
│ └── controller/DriverControllerIntegrationTest.java
🧠 Notes
•
•
•
All storage is in-memory (no DB required)
Straight-line Euclidean distance is used for driver matching
Thread-safe with ConcurrentHashMap and synchronized blocks
3
🪪 License
MIT License. Free to use and modify.
👨💻 Author
Developed by Guido Vendrame. Inspired by real-world ride-sharing platform requirements.
4
