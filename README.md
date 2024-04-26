# RESTful API Implementation

## Program Overview
This program is a RESTful API implementation using Spring Boot. It provides endpoints for managing user resources according to RESTful best practices.

## Data Format
Data is returned in JSON format following REST conventions. 

##Conveention urls:

https://jsonapi.org/

https://phauer.com/2015/restful-api-design-best-practices/

## User model

Id (auto-generated)

Email (required, validated)

First name (required)

Last name (required)

Birth date (required, >18)

Address

Phone number

## Endpoints

POST /api/v1/users/create: Create a new user.

PATCH /api/v1/users/update/{id}: Update user fields.

PUT /api/v1/users/update-all/{id}/: Update all user fields.

DELETE /api/v1/users/delete/{id}: Delete a user.

GET /api/v1/users/search: Search for users by birth date range.


## How to Run the Program

Clone the repository from GitHub or download a zip.

Navigate to the project directory.

Run the following command:
```
docker build -t clear-solutions-api .
docker-compose up
```

## Notes

Unit tests are included in the project to ensure code quality and reliability.

Error handling is implemented for REST endpoints to provide meaningful error messages.



