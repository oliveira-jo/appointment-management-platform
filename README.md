# Appointment Management API (Monorepo)

> Status: Developing

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Vscode](https://img.shields.io/badge/Vscode-007ACC?style=for-the-badge&logo=visual-studio-code&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37.svg?style=for-the-badge&logo=Postman&logoColor=white)
![Git](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white) 
![Angular](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white)


This monorepo contains a Spring Boot backend API for appointment management and an Angular frontend application. The backend provides endpoints for managing users (customers and professionals), products (services), and appointments. The frontend is a lightweight Angular client that consumes the API.

---

## Preview
![Preview](frontend/public/preview.jpg)

---

## 🚀 Technologies

- Backend: Java 21, Spring Boot, Spring Data JPA, H2 (test), Hibernate, Spring Security, JWT Authentication
- Frontend: Angular 17, TypeScript,RxJS, Angular Router, Bootstrap
- Build tools: Maven (backend), npm (frontend)
- Testing: JUnit 5, Mockito, Spring Test (MockMvc), TestRestTemplate
- Docker (optional containerization)

---

## 📋 Features

- User management (customers, professionals, admins)
- Product (service) catalog with duration and price
- Appointment scheduling, listing, metrics and cancellation
- Basic role-based security (JWT)

---

## 🏗️ Architecture

This is a monorepo with two main folders:

- `backend/` — Spring Boot REST API with domain, DTOs, services, repositories and controllers.
- `frontend/` — Angular application that consumes the REST API.

The backend follows a layered architecture: controller -> service -> repository -> domain.

````
Frontend (Angular)
    ↓
REST API (Spring Boot)
    ↓
Database
````
---

## 📁 Project Structure
````
appointment-management-monorepo/
│
├── backend/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
├── frontend/
│   ├── src/
│   ├── package.json
│   └── angular.json
│
├── README.md
└── .gitignore
````

---

## ⚙️ Prerequisites

- Java 21 (JDK)
- Maven (bundled wrapper `mvnw` is included)
- Node.js + npm (for frontend)
- Docker (optional, for containerized runs)

---

## 🔧 Installation

1. Clone the repository:

```bash
git clone https://github.com/oliveira-jo/appointment-management-api.git
cd appointment-management-api
```

2. Backend dependencies are managed with Maven. The repository contains `backend/mvnw` so you don't need a local Maven installation.

3. Install frontend dependencies:

```bash
cd frontend
npm install
cd ..
```

---

## 🔐 Environment Variables

Create an .env file or configure environment variables depending on your environment.

Example:
````
# JWT
JWT_SECRET=

# DATABASE
DATABASE_URL=
DATABASE_DB=
DATABASE_USERNAME=
DATABASE_PASSWORD=

POSTGRES_PORT=
POSTGRES_INTERNAL_PORT=

SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=

# TWILIO Environment Variables
TWILIO_ACCOUNT_SID=
TWILIO_AUTH_TOKEN=
TWILIO_WHATSAPP_NUMBER=
TWILIO_SMS_NUMBER=
TWILIO_TEMPLATE_SID=

# MAIL Environment Variables
MAIL_HOST=
MAIL_PORT=
MAIL_USERNAME=
MAIL_PASSWORD=
````

---

## ▶️ How to run

Backend (development):

```bash
cd backend
./mvnw spring-boot:run
```

This starts the API on the default port (configured in application.properties).

Frontend (development):

```bash
cd frontend
npm start
```

This serves the Angular app (check `frontend/package.json` for the exact script and port).

---

## 📡 API Endpoints

The API exposes endpoints under `/appointments`, `/products`, `/users`, same examples:

- GET /appointments — paginated list of appointments
- GET /appointments/day/{dd-MM-yyyy} — list appointments by day
- GET /appointments/professional/{id} — professional's appointments
- POST /appointments — create new appointment 
  (payload uses `AppointmentMinDTO` date format `dd/MM/yyyy HH:mm:ss`)
- GET /products — list products
- POST /products — create a product

Note: Check controller sources for full route definitions and request/response DTOs.

---

## 📑 API Documentation

Swagger/OpenAPI documentation:

---

## 🔐 Security

The backend uses JWT-based authentication and role-based authorization. Tests use `@WithMockUser` in controller unit tests when needed. For manual testing, obtain a JWT token from the authentication endpoint.

## Authentication (JWT)
Login Request
````
POST /auth/login
Content-Type: application/json
{
  "email": "admin@email.com",
  "password": "123456"
}
````

Response
````
{
  "id": "12345678-1234-1234-1234-123456781010",
  "email": "admin@",
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 1774036219994
}
````

Using the Token
````
Authorization: Bearer YOUR_TOKEN
````

---

## 🧪 Tests

Run backend unit tests (Maven):

```bash
cd backend
./mvnw test
```

Integration tests (if configured with Failsafe) can be run with:

```bash
cd backend
./mvnw verify
```

Frontend tests:

```bash
cd frontend
npm test
```

Notes:
- Some controller tests expect specific date formats. For `AppointmentMinDTO`, the `scheduledAt` field expects `dd/MM/yyyy HH:mm:ss` due to `@JsonFormat` on the DTO.
- If tests load the Spring context, ensure your test profile (`application-test.properties`) has appropriate in-memory DB or test settings.

---

## 🐳 Docker

If Docker files are provided (check repo), you can build and run images. Example pattern:

```bash
# Build backend image (if Dockerfile exists)
cd backend
docker build -t appointment-management-api-backend .

# Run backend
docker run -p 8080:8080 appointment-management-api-backend
```

## 👨‍💻 Author

Name: Jonathan Oliveira 
Contact: devjoliveira@gmail.com
