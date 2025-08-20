# User Manager Service

## Overview

The **User Manager Service** is a Spring Boot application that provides user authentication and management functionalities. It includes features such as user registration, login, and token-based authentication using JWT.

---

## Features

- **User Registration**: Allows users to sign up with a username, password, and email.
- **User Login**: Authenticates users and generates a JWT token for session management.
- **JWT Authentication**: Secures endpoints using stateless JWT-based authentication.
- **Validation**: Handles input validation and error responses for invalid requests.

---

## Technologies Used

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Gradle** (Build Tool)
- **H2 Database** (In-memory database for development/testing)
- **Lombok** (For reducing boilerplate code)

---

## Prerequisites

Before setting up the project, ensure you have the following installed:

1. **Java Development Kit (JDK)**: Version 17 or higher.
2. **Gradle**: Version 7.x or higher.
3. **IntelliJ IDEA** (or any preferred IDE).
4. **Git**: For version control.

---

## Setup Instructions

Follow these steps to set up and run the project locally:

### 1. Clone the Repository

```bash
git clone https://github.com/zamoranoalicia/user-manager-service.git
cd user-'''manager-service
```

### 2. Build the Project
Run the following command to build the project:
```bash
./gradlew build
```
### 3. Run the Application
Start the application using the following command:

```bash
./gradlew bootRun
```

The application will start on http://localhost:8080.


### 4. Test the Endpoints
   You can use tools like Postman or cURL to test the API endpoints. Below are the available endpoints:  
   Endpoints:

#### API Endpoints

| HTTP Method | Endpoint     | Description              | Authentication Required |
|-------------|--------------|--------------------------|--------------------------|
| **POST**    | `/auth/signup` | Register a new user       | No                       |
| **POST**    | `/auth/login`  | Login and get JWT token   | No                       |
| **GET**     | `/secured`     | Example secured endpoint  | Yes                      |

### 4. Configuration
#### 4.1 Application Properties
 * The application uses the default application.properties file located in src/main/resources.
You can modify it to configure database settings, JWT secret, etc.
 * Additionally  it is possible to define properties for the _domain_ validation, _password_ regex validation, and the error message to be shown
 The default configuration is the following

```yaml
  config:
    user:
      domain : 'dominio.cl'
    password:
      regex: ^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$
      errorMessage: |
            At least one lowercase letter is required.
            At least one uppercase letter is required.
            At least one digit is required.
            At least one special character (@$!%*?&) is required.
            Minimum length of 8 characters is required.
```

#### 4.2 JWT Configuration
The JWT secret and expiration time can be configured in the application.properties file:

```bash
jwt.apiKey=your-secret-key-this-should-be-a-base64_encode
jwt.expiration=3600000
```
### 5. Running Test

To run the unit tests, execute the following command

```bash
./gradlew test
```

### 6. Swagger
The API is configured to use SWAGGER UI, and also API Docs

* Local Swagger URL: http://localhost:8080/swagger-ui/index.html#/
* Local API docs URL: http://localhost:8080/v3/api-docs
### 6. Project Structure

* **src/main/java**: Contains the main application code.
    * config: Configuration files 
    * controller: REST controllers for handling API requests.
    * service: Business logic and services.
    * config: Security and application configuration.
    * entity: Entity classes for database models.
    * dto: Data Transfer Objects for API communication.
* src/test/java: Contains unit and integration tests.

### Contact
For any questions or issues, please contact zamoranoalicia via GitHub.