# Smart Campus REST API

## Overview

This project is a Smart Campus RESTful API developed using Java and JAX-RS (Jersey). It simulates a system for managing rooms, sensors, and sensor readings within a campus environment.

The API follows REST principles including proper use of HTTP methods, status codes, and resource-based design. It also implements advanced features such as filtering, sub-resources, exception handling, and logging.

---

## API Base URL

```
http://localhost:8080/api/v1
```

---

## Key Features

* Room management (create, retrieve, delete)
* Sensor management with validation
* Filtering using query parameters
* Nested sub-resources for sensor readings
* Centralized error handling with Exception Mappers
* Request and response logging using JAX-RS filters
* HATEOAS-based discovery endpoint

---

## Technologies Used

* Java 17
* JAX-RS (Jersey 2.35)
* Grizzly HTTP Server (Embedded)
* Jackson (JSON processing)
* Maven

---

## How to Build and Run the Project

### 1. Clone the repository

```
git clone https://github.com/dulesinij/smart-campus-api.git
cd smart-campus-api
```

---

### 2. Build the project

```
mvn clean install
```

---

### 3. Run the application

Run the `Main.java` class.

You should see:

```
API started at: http://localhost:8080/api/v1
```

---

### 4. Test the API

Use Postman or curl to interact with the endpoints.

---

## Sample curl Commands

### 1. Discovery Endpoint

```
curl -X GET http://localhost:8080/api/v1
```

---

### 2. Create a Room

```
curl -X POST http://localhost:8080/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"R1","name":"Room A","capacity":30}'
```

---

### 3. Get a Room

```
curl -X GET http://localhost:8080/api/v1/rooms/R1
```

---

### 4. Create a Sensor

```
curl -X POST http://localhost:8080/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"S1","type":"CO2","status":"ACTIVE","currentValue":0,"roomId":"R1"}'
```

---

### 5. Filter Sensors

```
curl -X GET "http://localhost:8080/api/v1/sensors?type=CO2"
```

---

### 6. Add a Sensor Reading

```
curl -X POST http://localhost:8080/api/v1/sensors/S1/readings \
-H "Content-Type: application/json" \
-d '{"id":"READ1","timestamp":1710000000000,"value":25.5}'
```

---

### 7. Get Sensor Readings

```
curl -X GET http://localhost:8080/api/v1/sensors/S1/readings
```

---

### 8. Delete a Room

```
curl -X DELETE http://localhost:8080/api/v1/rooms/R1
```

---

## Error Handling

The API uses custom Exception Mappers to ensure that all errors are returned in a structured JSON format. It handles:

* 409 Conflict (Room has active sensors)
* 422 Unprocessable Entity (Invalid references)
* 403 Forbidden (Sensor unavailable)
* 500 Internal Server Error (Unexpected failures)

No internal Java stack traces are exposed to the client.

---

## Logging

A custom JAX-RS filter is implemented to log:

* Incoming request method and URI
* Outgoing response status code

This provides centralized logging without duplicating code in each resource method.

---

## Notes

* This project uses an embedded Grizzly server for simplicity and ease of execution.
* All data is stored in-memory using collections such as maps and lists.

---

## Author

Dulesini Jayathilaka
