# Expense Tracker (Spring Boot)

A simple REST API built using Spring Boot, Spring Data JPA, and PostgreSQL.

## Features
- Create, read, update, delete expenses
- Get expense by ID
- Get total expense count
- Get total amount grouped by category

## Tech Stack
- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven

## Project Structure
src/main/java/com/prac/practice  
controller  
service  
repository  
entity  
PracticeApplication.java  

## Configuration

application.properties

spring.application.name=practice  
spring.datasource.url=jdbc:postgresql://localhost:5432/expense_db  
spring.datasource.username=postgres  
spring.datasource.password=ur_password  

spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true  

## API Endpoints

POST   /expenses  
GET    /expenses  
GET    /expenses/id/{id}  
PUT    /expenses/id/{id}  
DELETE /expenses/id/{id}  
GET    /expenses/count  
GET    /expenses/total-by-category  

## Sample Request Body

{
  "title": "Coffee",
  "category": "Food",
  "amount": 120
}

## How to Run
- Create PostgreSQL database named expense_db
- Update database credentials
- Run the Spring Boot application
- Access APIs at http://localhost:8080
