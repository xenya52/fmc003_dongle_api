# FMC003 Rest API
---------------

A REST API that is easy to understand. 
Sidenote: I aim to demonstrate a clean and efficient structure for a typical REST API application

Overview
---------------

This repository provides a restapi to get the data from the db of your choice and make it available as easy-to-understand restful apis.

Table of Contents
---------------

- [Getting Started](#Getting-Started)
- [Project Structure](#Project-Strucutre)
- [Endpoints](#Endpoints)
- [Technologies Used](#Technologies-Used)
- [Contributing](#Contributing)

Getting Started
---------------

To get a local copy of the project up and running, follow these steps.

### Prerequisites


- Java 11 or higher
- Docker (optional for containerization)

### Installation

1. Clone the repository
```
  git clone https://github.com/xenya52/fmc003_rest-api.git

```
2. Navigate to the project directory
```
   cd fmc003_rest-api

```
3. Build the project
```
  ./mvnw clean install

```
4. Run the application
```
  ./mvnw spring-boot:run

```
Project Structure
---------------

The project is divided into multiple layers to follow the best practices and ensure the separation of concerns.

### Model

The model package contains the classes that represent the data of the application.

### Service

The service package is responsible for implementing the business logic of the application. It acts as an intermediary between the controller and the repository layers.

### Controller

The controller package manages the HTTP requests and responses, handling the incoming API calls.

### Endpoints

The application exposes various REST API endpoints to interact with the data. Below is a brief overview of the available endpoints:

- GET /api/data - Retrieve all data
- POST /api/data - Create new data
- PUT /api/data/{id} - Update existing data
- DELETE /api/data/{id} - Delete data by ID

Technologies Used
---------------

- Java
- Spring Boot
- MongoDB
- Docker

Contributing
---------------

Contributions are welcome! Please follow the standard GitHub flow for contributing:

1. Fork the repository
2. Create a new branch (git checkout -b feature/YourFeature)
3. Commit your changes (git commit -m 'Add some feature')
4. Push to the branch (git push origin feature/YourFeature)
5. Open a pull request
