# My Spring Boot Project

## Introduction

A brief introduction to VCList project.
I used this app for creating voters list and I've used in project to enter we need a JWt token.
I implemented crud api's and signup and login otp.

## Prerequisites

I implemented these project in Spring boot(sts 4.0)

## Installation

Steps to install the project.
File -> New -> Starter Project -> VCList

## Dependencies
mongodb
spring web
devtools
spring security
swagger
jjwt-jackson
spring-boot-starter-mail
spring-cloud-starter-netflix-eureka-client
spring-boot-starter-validation

## Running the Application

Right click on project in sts -> Run as -> Spring boot app

## Jwt Authentication

http://localhost:9998/voter/authenticate
using above url we can generate Jwt token and It'll generate by
like user or admin access 

User Token
----------
{
    "username":"DAD",
    "password":"dad"
}

Admin Token
-----------

{
    "username":"MOM",
    "password":"mom"
}

## Controllers

### 1. **VCcontroller**

**Description:**  
`VCcontroller` is responsible for handling requests related to [explain purpose].

**Endpoints:**

| Method | URL | Description |
|--------|-----|-------------|
| GET    | `http://localhost:9998/voter/getvoter`     | Retrieves [resource] |
| POST   | `http://localhost:9998/voter/addvoter`     | Creates a new [resource] |
| PUT    | `http://localhost:9998/voter/updateName/{name}` | Updates an existing [resource] |
| DELETE | `http://localhost:9998/voter/delete/{id}` | Deletes an [resource] by ID |


