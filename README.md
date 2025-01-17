# FMC003 Rest API

A REST API designed to be easy to understand, demonstrating a clean and efficient structure for a typical REST API application.

Overview
---------

This repository provides a REST API to fetch data from the database of your choice and make it available as easy-to-understand RESTful APIs.

Table of Contents
---------

- [Getting Started](Getting-Started)
- [Project Structure](Project-Structure)
- [Endpoints](Endpoints)
- [Technologies Used](Technologies-Used)
- [Contributing](Contributing)
- [License](License)
- [Contact](Contact)

Getting Started
---------

To get a local copy of the project up and running, follow these steps.

### Prerequisites

- Java 11 or higher
- Docker (optional for containerization)

### Installation

1. Clone the repository:
``` bash
git clone https://github.com/xenya52/fmc003_rest-api.git
```
2. Navigate to the project directory:
``` bash
cd fmc003_rest-api
```
3. Build the project:
``` bash
./mvnw clean install
```
4. Run the application:
```bash
./mvnw spring-boot:run
```

Project Structure
---------

The project is divided into multiple layers to follow best practices and ensure the separation of concerns.

### Model

The model package contains the classes that represent the data of the application.

### Service

The service package is responsible for implementing the business logic of the application. It acts as an intermediary between the controller and the repository layers.

### Controller
The controller package manages HTTP requests and responses, handling the incoming API calls.

### Endpoints
The application exposes various REST API endpoints to interact with the data. Below is a brief overview of the available endpoints:

- ```GET /api/data``` - Retrieve all data
- ```POST /api/data``` - Create new data
- ```PUT /api/data/{id}``` - Update existing data
- ```DELETE /api/data/{id}``` - Delete data by ID

Technologies Used
---------

- Java
- Spring Boot
- MongoDB
- Docker

Contributing
---------

Contributions are welcome! Please follow the standard GitHub flow for contributing:

1. Fork the repository
2. Create a new branch (```git checkout -b feature/YourFeature```)
3. Commit your changes (```git commit -m "Add some feature"```)
4. Push to the branch (```git push origin feature/YourFeature```)
5. Open a pull request
