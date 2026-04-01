# Appointment Management platform

> Status: Developing

A full-stack appointment management system designed to handle scheduling, users, services, and business metrics.

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Vscode](https://img.shields.io/badge/Vscode-007ACC?style=for-the-badge&logo=visual-studio-code&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37.svg?style=for-the-badge&logo=Postman&logoColor=white)
![Git](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white) 
![Angular](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white)

## Objective
The main goal of this project is to build a scalable API and modern UI for managing appointments efficiently.

## Domain
The system is centered around appointments, with the following entities:
- Customer
- Professional
- Service (Product)
- Appointment 
- Metrics

## Preview
![Preview](frontend/public/preview.jpg)

## Monorepo Structure
This project follows a monorepo architecture, keeping frontend and backend together:
````
  appointment-management/
  ├── backend/   (Spring Boot + JWT + Clean Architecture)
  ├── frontend/  (Angular)
  ├── docker-compose.yml
  └── README.md
````

...


# BACKEND (Spring Boot)
Architecture
- Controllers (DTO layer)
- Services (Business rules)
- Repositories (Data access)
- Models / Entities
- Security (JWT + Spring Security)
- Infrastructure layer
- Scheduled Jobs

## Background Jobs
- Automatically update appointment status
  * CONFIRMED → COMPLETED
- Remove old appointments (older than 6 months)
- Send Reminder Appointment One Day Before (Twilio and Mail)

## Appointment Service Logic
1. Retrieve the service duration
2. Calculate endsAt
3. Validate scheduling conflicts:
````
  novoInicio < existenteFim
  novoFim > existenteInicio
````
 4. Save appointment

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

...


# FRONTEND
- Auth com JWT (backend)
- HTTP Interceptor para token
- Auth Guard
- Role-based UI (ADMIN vs PROFESSIONAL)
- Loading states
- Error handling centralizado
- Toast notifications
- Reactive Forms
- Form validation elegante

## Frontend Tecnologies
- Angular — Application framework 
- Bootstrap — Responsive and Styles
- AdminLTE — Admin Template with base in Bootstrap
- jQuery — DOM Manipulate (necessary for same plugins of AdminLTE)
- Font Awesome — Icons for interface 

## Frontend Structure
````
src/app/
 ├── core/        (auth, interceptors, guards)
 ├── shared/      (components reutilizáveis)
 ├── features/
 │    ├── appointments/
 │    ├── customers/
 │    ├── professionals/
 │    └── products/
 └── layout/
````

## Configuração no Angular
To ensure the correct functioning of the styles and scrypts, the following file have been added to angular.json:

+ Styles
````JSON
"styles": [
  "node_modules/@fortawesome/fontawesome-free/css/all.min.css",
  "node_modules/admin-lte/dist/css/adminlte.min.css",
  "node_modules/bootstrap/dist/css/bootstrap.min.css",
  "src/styles.css"
]
````

+ Scripts
````JSON
"scripts": [
  "node_modules/jquery/dist/jquery.min.js",
  "node_modules/bootstrap/dist/js/bootstrap.bundle.min.js",
  "node_modules/admin-lte/dist/js/adminlte.min.js"
]
````

## Dark Mode
The system has dark mode support, througth the global class
````CSS
body.dark-mode {
  background-color: #1e1e2f;
  color: #f1f3f5;
}
````

Example of component customization
````CSS
body.dark-mode .modal {
  background-color: #2b3035;
  color: #f1f3f5;
}

body.dark-mode .table {
  background-color: #2b3035;
  color: #f1f3f5;
}
````

### Observações Importantes
- The AdminLTE depend of jQuery for it's complete work
- The plugins (how tables, modail, etc.) need the scripts loaded correctly
- Samo componetns need manual inicialization by jQuery


### Applied Best Practices
- Separation of assets into src/assets
- Use of Bootstrap for responsiveness
- Visual standardization with AdminLTE
- Customization via global CSS (dark mode)

### Possible Future Improvements
- Remove jQuery dependency (use pure Angular)
- Create reusable components (cards, modals, tables)
- Implement dynamic theming (persistent dark/light toggle)
- Migrate to modern libraries such as:
  * Angular Material
  * Tailwind CSS

...


# RUNING THE PROJECT IDE
1. Backend
````
  cd backend
  ./mvnw spring-boot:run
````
 - Backend runs on:
````
  http://localhost:8080
````
2. Frontend
````
  cd frontend
  npm install
  ng serve
````


...


# DOCKER SETUP
This project uses Docker to orchestrate the intare application, indluding:
- PostgreSQL (Banco de dados)
- Spring Boot (Backend)
- Angular (Frontend)


## Estrutura do Projeto
```
appointment-management-api/
├── backend/
│   ├── Dockerfile
│   └── target/app.jar
├── frontend/
│   ├── Dockerfile
│   └── ...
├── docker-compose.yml
├── .env
```


## Variáveis de Ambiente
Generate a file of `.env` in root of project:
>  ATTENTION: Generate your own strong environment variables; this is just an example.

```env
POSTGRES_DB=appointment_db
POSTGRES_USER=admin
POSTGRES_PASSWORD=admin123

POSTGRES_PORT=5433
POSTGRES_INTERNAL_PORT=5432

SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/appointment_db
SPRING_DATASOURCE_USERNAME=admin
SPRING_DATASOURCE_PASSWORD=admin123
```


## PostgreSQL (Banco de Dados)
- Porta externa: `${POSTGRES_PORT}` (ex: 5433)
- Porta interna: `5432`
- Dados persistidos via volume Docker

...


## BACKEND (Spring Boot)

### Dockerfile

```dockerfile
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Build da aplicação
Before the container up

```bash
cd backend
mvn clean package
```


...


## FRONTEND (Angular)

### Dockerfile

```dockerfile
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
FROM nginx:alpine
COPY --from=build /app/dist/<nome-do-projeto> /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

> ATENTION: Substitua `<nome-do-projeto>` pelo nome correto gerado na pasta `dist`.


## Comunicação entre serviços
Dentro do Docker:

| Serviço    | Host       |
| ---------- | ---------- |
| PostgreSQL | `postgres` |
| Backend    | `backend`  |
| Frontend   | `frontend` |


## Configuração da API no Angular
> ATENTION: Inside the frontend, use:

```ts
apiUrl = 'http://backend:8080';
```

# docker-compose.yml

```yaml
services:

  postgres:
    image: postgres:15
    container_name: postgres-db
    restart: always
    environment:
      POSTGRES_DB: ${POSTGRES_DB}
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
    ports:
      - "${POSTGRES_PORT}:${POSTGRES_INTERNAL_PORT}"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  backend:
    build: ./backend
    container_name: spring-api
    restart: always
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    environment:
      JWT_SECRET: ${JWT_SECRET}
      DATABASE_URL: ${DATABASE_URL}
      DATABASE_USERNAME: ${DATABASE_USERNAME}
      DATABASE_PASSWORD: ${DATABASE_PASSWORD}
      POSTGRES_INTERNAL_PORT: ${POSTGRES_INTERNAL_PORT}
      SPRING_DATASOURCE_URL: ${SPRING_DATASOURCE_URL}
      SPRING_DATASOURCE_USERNAME: ${SPRING_DATASOURCE_USERNAME}
      SPRING_DATASOURCE_PASSWORD: ${SPRING_DATASOURCE_PASSWORD}
      MAIL_HOST: ${MAIL_HOST}
      MAIL_PORT: ${MAIL_PORT}
      MAIL_USERNAME: ${MAIL_USERNAME}
      MAIL_PASSWORD: ${MAIL_PASSWORD}

  frontend:
    build: ./frontend
    container_name: angular-app
    restart: always
    ports:
      - "4200:80"
    depends_on:
      - backend

volumes:
  postgres_data:
```


## Configurate environment

- Backend: application.properties: 
````
spring.profiles.active=docker
````

- Frontend: environments.prod.ts
- if using a localhosts
````ts
export const environment = {
  production: true,
  apiUrl: 'http://localhost:8080/api/v1'
};
````

- if using a your-domain-server
````ts
export const environment = {
  production: true,
  apiUrl: 'http://your-domain-server:8080/api/v1'
};
````


...


# HOW TO EXECUTE THE PROJECT
## 1. Build do backend
```bash
cd backend
mvn clean package
cd ..
```

## 2. BRING UP THE CONTAINERS
```bash
docker compose up --build -d
```

## 3. ACCESS THE SERVICES
* Frontend: http://localhost:4200
* Backend: http://localhost:8080
* PostgreSQL: localhost:${POSTGRES_PORT}

# USEFUL COMMANDS
## View running containers
```bash
docker ps
```

## Stop containers
```bash
docker compose down
```

## See logs
```bash
docker compose logs -f
```

# Commun Problens
##  *Erro* of database conection

* Check if the backend is using:
  ```
  postgres:5432
  ```

##  *Erro*  Java Version

* Make sure to use Java 21 in the Dockerfile

## *Variables* `.env` not loagind

* The file `.env` must to be in the project root

## *CORS* in backend

* Configure CORS in Spring Boot


## Future Improvements
_ Add pgAdmin
_ Configure Nginx as a gateway
_ Implement Flyway/Liquibase
_ Create profiles (dev/prod)
_ Deploy to the cloud (AWS, Railway, VPS)


## Final Result
Complete environment with:
- Persistent database
- Containerized backend
- Frontend served via Nginx
- Internal communication via Docker network




