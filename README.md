# File Management API
This project provides a RESTful API for file storage and listing operations. The API stores files on the server, keeps file information in a database, and ensures secure access using JWT authentication.

## Technologies 
  - Java 17
  - Spring Boot 3.1.1
  - Maven

## Prerequisites
Make sure the following software is installed to run this project:
  - Java JDK 17
  - Maven

## Configuration
Follow the steps below for project configuration:
  - Open the application.properties file: src/main/resources/application.properties
  - Configure the database connection details: spring.datasource.url, spring.datasource.username, spring.datasource.password
  - Set up JWT configuration: jwt.secret, jwt.expirationTime

## API Documentation
API documentation is provided through Swagger. Visit the following URL to access the API documentation:
  - http://localhost:8080/swagger-ui.html
