# HELPDESK PROJECT

> Status: Developing

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Vscode](https://img.shields.io/badge/Vscode-007ACC?style=for-the-badge&logo=visual-studio-code&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37.svg?style=for-the-badge&logo=Postman&logoColor=white)
![Git](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white) 


## Objective
The main objective of this project is the development an API to provide a system of Appointments management. 


## Domain
Appointment management 
* Customer 
* Professional 
* Service 
* Appointment 


## The mean of system is:
* Appointment


## Monorepo
> developing
* Version frontend and backend together
* Create a single pipeline in CI/CD
* Create a single Docker Compose file
* Keep API and UI contracts synchronized


## Structure
````
appointment-management/
 ├── backend/  (Spring Boot + JWT + Clean Architecture)
 ├── frontend/ (Angular)
 ├── docker-compose.yml
 └── README.md (explicando arquitetura)
````


## BACHEND
+ CONTROLLERS (DTO's)
+ SERVICES (DTO's)
+ REPOSITORIES (Entities)
+ MODELS (Entities)
+ INFRA


## FRONTEND:
> developing
+ Auth com JWT (usando seu backend)
+ HTTP Interceptor para token
+ Auth Guard
+ Role-based UI (ex: ADMIN vs PROFESSIONAL)
+ Loading states
+ Error handling centralizado
+ Toast notifications
+ Reactive Forms
+ Form validation elegante
+ UI moderna (Angular Material ou Tailwind)


## Technologies Used
* Java
* Spring Boot Starter Web: Servidor Tomcat
* Spring Boot Devtools: Hot Heload 
* Spring Boot Starter Data JPA: Persistence
* H2


## Configuring the Database
### In the file Application.properties

1. application.properties

````
spring.profiles.active=test
spring.jpa.open-in-view=false

````

2. application-test.properties

````
# H2 connection
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# H2 client
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Show sql
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
````


## Problen with autosave in import.sql - vscode Solve with 
 1. Mac: Cmd + Shift + P
 2. Preferences: Open Settings (JSON)
 3. Dentro do settings.json
 
 ```
 "[sql]": {
    "editor.formatOnSave": false
  }
 ```

## Structure of Appointment Service
 1. Search the product (get the durationInSeconds)
 2. Calculet the endsAt
 3. Verify conflicts
  - Conflict rules: 
  ````
  novoInicio < existenteFim
  
  novoFim > existenteInicio
  ````
 4. Save

 ## Features to be implanted 
 - spring security and jwt token


 