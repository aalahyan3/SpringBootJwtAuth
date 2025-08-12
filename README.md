# springboot-jwt-auth

## Overview
This is a Spring Boot REST API project implementing stateless JWT (JSON Web Token) authentication and authorization using Spring Security.  
It provides endpoints for user registration, login, and a protected endpoint that returns user information based on the JWT token.

## Features
- User registration with password hashing  
- User login returning JWT tokens in HTTP headers  
- JWT validation and token-based authentication filter  
- Stateless session management (no server-side session)  

## Technologies Used
- Java 17  
- Spring Boot 
- Spring Security  
- jjwt (Java JWT library)  
- Maven
- JPA / Hibernate 
- PostgreSQL


## actions
Users can only access protected resources if authenticated with a valid JWT token. The application uses a database with a users table to manage user registration and login via the `/api/auth` endpoints. The `/api/auth/whoami` endpoint extracts the username from the token and returns it, or `"unknown"` if the user is not authenticated. Other endpoints like `/{name}` are protected and require authentication; for testing, `/{name}` returns the provided name along with a random age value.
