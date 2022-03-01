# User Registration Application

### Introduction

This is a springboot API that exposes three services :
- One that allows to register a user
- One that displays the details of a registred user using its id
- One that displays the details of all the registred users 

Where a user is defined by :

- a username
- a birthdate
- a country of residence

And two other optional attributes :

- a phone number
- a gender

Only adult French residents are allowed to create an account

### Requirements

- Java 8
- Maven 3.6.3
- [Lombock installation](https://algoclinic.com/install-lombok.html)


### Steps to Setup

1. Clone the application:  https://github.com/hajarrs/userRegistration.git

2. Import the project in Eclipse or another IDE by selecting Existing Maven Project

3. Change database username and password : 
    - open userApp/src/main/resources/application.properties
    - change spring.datasource.username and spring.datasource.password with your credentials or you can keep this configuration and use this credentials to connect to H2

4. Build and run the app using Spring Boot App : userApp/src/main/java/com/airfrance/userApp/UserAppApplication.java

### Explore Rest APIs

The app defines the following APIs.

- GET http://localhost:8080/api/

- POST http://localhost:8080/api/

- GET http://localhost:8080/api/{userId}

The user JSON format :
```json
[
  {
    "birthday": "string",
    "country": "string",
    "gender": "MALE",
    "id": 0,
    "phoneNumber": "string",
    "username": "string"
  }
]
```
Where Gender can take the following values :
- MALE
- FEMALE
- OTHER

The only acceptable country is : French

### Important Links :
- H2 : http://localhost:8080/h2-ui
- Swagger UI : http://localhost:8080/swagger-ui.html


