# FMC003 Rest API

A REST API that is easy to understand.
Sidenote: I aim to demonstrate a clean and efficient structure for a typical REST API application

Overview
---------

This repository provides a REST API to fetch data from the database of your choice and make it available as easy-to-understand RESTful APIs. To use this repo its necessary to host MongoDB in some way. I decided to host it with a docker compose file because its the simpliest way.

Table of Contents
---------

- [Getting Started](Getting-Started)
- [Prerequisites](Prerequisites)
- [Installation](Installation)
- [Project Structure](Project-Structure)
- [Model](Model)
- [Service](Service)
- [Controller](Controller)
- [Endpoints](Endpoints)
- [Technologies Used](Technologies-Used)
- [Contributing](Contributing)

Getting Started
---------

To get a local copy of the project up and running, follow these steps. 
(This will change soon, because Im not satisfied with my work here, so here is it possible to collaborate)

Prerequisites
---------

- Java 11 or higher
- maven 3 or higher
- Docker (optional for containerization)

Installation
---------

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

After that you can go on localhost:8080/api-docs to see a advanced view of all APIs and responses. Additionally you can open up http://localhost:8080/swagger-ui/index.html#/ to interact with the swagger ui. More informations about the APIs are down below. Thanks for setting up my program. <3

Project Structure
---------

The project is divided into multiple layers to follow best practices and ensure the separation of concerns.

Model
---------

The model package contains the classes that represent the data of the application.

The ```IoDongleModel``` class is a Java model representing a device. It includes the following:

- **Attributes**:
  - ```deviceId```: A long value representing the Device ID.
  - ```dongleValues```: A Map<String, String> the key is the id of a IoWikiModel and the value, as the name said, the value.


The ```IoWikiModel``` class is a Java model representing a wiki entry from the left Site to explain the id that you get. It includes the following:

- **Attributes**:
  - ```wikiId```: A string representing the Wiki ID.
  - ```wikiName```: A string representing the Wiki Name.
  - ```wikiDescription```: A string representing the Wiki Description.
  - ```wikiType```: A string representing the Wiki Type.
  - ```multiplier```: A string representing a multiplier value.
  - ```valMin```: A string representing the minimum value.
  - ```valMax```: A string representing the maximum value.
  - ```unit```: A string representing the unit.

- **Constructors**:
  - Multiple constructors allow initializing the object with various combinations of attributes.

For more details, you can view the [file](https://github.com/xenya52/fmc003_rest-api/blob/6ac4369bb630b6df1e0e1027347faac6b779bd8e/src/main/java/com/xenya52/fmc003_rest_api/model/IoWikiModel.java) here.

**Key Differences**

- ```IoDongleModel``` includes a list of IoWikiModel objects and methods to handle JSON serialization and deserialization.
- ```IoWikiModel``` focuses on representing wiki entries with various attributes and provides multiple constructors for flexibility.

Service
---------

<<<<<<< HEAD
The service package is responsible for implementing the business logic of the application. It acts as an intermediary between the controller and the repository layers. The service layer ensures that the data flow is managed correctly and that the business rules are applied consistently.
=======
The service package is responsible for implementing the business logic of the application. It acts as an intermediary between the controller and the repository layers.
It is separated into two parts.

### IoWiki

Heres everything what is important to parse the key values of the dongle model into readable informations with that you can work with in the frontend. Furthermore heres the method stored to fetch the required values from the teltonika io wiki page.

### IoDongle

The IoDongle folder is responsible for accepting dongleDate and implementing functions to get some dumby data for debugging reasons.
>>>>>>> 8b37da380e7df636f7d34ef0f5c4679ac4875084

Controller
---------

The controller package manages HTTP requests and responses, handling the incoming API calls. My Controller presenting the following endpoints down below for IoWikiModels and IoDongleModels. 

Repository
---------

This repository is designed to provide a robust and scalable REST API solution for managing IoWiki and IoDongle models. It leverages the power of Java and Spring Boot to create a clean and efficient structure, ensuring that the API is easy to understand and extend. The repository also includes comprehensive documentation and a well-organized project structure, making it simple for developers to get started and contribute. With support for MongoDB and optional Docker containerization, this project is equipped to handle a variety of deployment scenarios. Whether you are looking to fetch data, create new entries, or update existing ones, this REST API provides all the necessary endpoints to interact with your data seamlessly.


Endpoints
---------

The application exposes various REST API endpoints to interact with the data. Below is a brief overview of the available endpoints:

### IoWiki Endpoints

#### v1
- ```GET /v1/io-wiki/items/{listOfIds}``` - Show IoWikimodels from valid id reverences in the DB
- ```GET /v1/io-wiki/items/all``` - Show all IoWikiModels in the DB

#### v2
- ```GET /v2/io-wiki/items/{listOfIds}``` - Show IoWikimodels from valid id reverences in the DB
- ```GET /v2/io-wiki/items/all``` - Show all IoWikiModels in the DB
- ```POST /v2/io-wiki/fetch-default-values-into-db``` - Fetches the presaved values, from a file, into the MongoDB
- ```POST /v2/io-wiki/fetch-advanced-values-into-db``` - Fetches the scraped teltonikaIoPage, from a file, into the MongoDB as IoWikiModels
- ```POST /v2/io-wiki/fetch-teltonika-io-wiki-into-file``` - Fetches the scraped teltonikaIoPage, into the dataSendingParameters file in the resources folder, only works every 24 hours, resets at midnight
- ```POST /v2/io-wiki/create``` - Creates a new IoWikiModel in the DB with the given values
- ```POST /v2/io-wiki/update/{id}``` - Updates the IoWikiModel in the DB with the given id
- ```DELETE /v2/io-wiki/delete/{id}``` - Deletes the IoWikiModel in the DB with the given id

### IoDongle Endpoints

#### v1
- ```GET /v1/dongle/items/{listOfIds}``` - Show IoDongleModels from valid id reverences in the DB
- ```GET /v1/dongle/items/all``` - Show all IoDongleModels in the DB

#### v2
- ```GET /v2/dongle/items/{listOfIds}``` - Show IoDongleModels from valid id reverences in the DB
- ```GET /v2/dongle/items/all``` - Show all IoDongleModels in the DB
- ```POST /v2/dongle/fetch-default-values-into-db``` - Fetches the presaved values, from a file, into the MongoDB
- ```POST /v2/dongle/create``` - Creates a new IoDongleModel in the DB with the given values
- ```POST /v2/dongle/update/{id}``` - Updates the IoDongleModel in the DB with the given id
- ```DELETE /v2/dongle/delete/{id}``` - Deletes the IoDongleModel in the DB with the given id
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
1. Fork the repository
2. Create a new branch (```git checkout -b feature/YourFeature```)
3. Commit your changes (```git commit -m "Add some feature"```)
4. Push to the branch (```git push origin feature/YourFeature```)
5. Open a pull request
