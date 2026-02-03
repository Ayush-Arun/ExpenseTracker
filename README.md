# Expense Tracker (Spring Boot)

Simple REST API built using Spring Boot, Spring Data JPA, and PostgreSQL.

## Features
- CRUD operations for expenses
- Get expense by ID
- Get total expense count
- Get total amount grouped by category


## Tech Stack
- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL


## Project Structure
- src/main/java/com/prac/practice
  - controller
  - service
  - repository
  - entity
- PracticeApplication.java

  
## How to Run Locally
1. Clone the repository
2. Create PostgreSQL database named `expense_db`
3. Update credentials in `application.properties`
4. Mainly password from `application.properties`
5. Run the application with:
   mvn spring-boot:run
- Base URL: http://localhost:8080/expenses


## API Endpoints (Test using Postman)

The following operations can be tested using Postman.

| Method | Endpoint | Purpose |
|------|--------|---------|
| POST | /expenses | Create an expense |
| GET | /expenses | Get all expenses |
| GET | /expenses/id/{id} | Get expense by ID |
| PUT | /expenses/id/{id} | Update an expense |
| DELETE | /expenses/id/{id} | Delete an expense |
| GET | /expenses/count | Get total number of expenses |
| GET | /expenses/total-by-category | Get total amount grouped by category |


## Sample JSON
{
  "title": "Coffee",
  "category": "Food",
  "amount": 120
}
