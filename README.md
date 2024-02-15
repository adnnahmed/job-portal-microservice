# Job Portal Microservices Backend

This repository contains the backend solution for a basic job portal system implemented as microservices using the Spring framework and Java. The microservices architecture is facilitated by Spring Cloud, and various tools such as Eureka, OpenFeign, Zipkin, Micrometer, and API Gateway have been incorporated for service discovery, communication, distributed tracing, monitoring, and API routing.

## Microservices Architecture

The backend is structured as a set of microservices, each serving a specific purpose within the job portal system. The microservices include:

-   Job Service: Manages job-related functionalities such as creating, updating, and retrieving job postings.
-   Company Service: Handles company-related operations, including company profiles and details.
-   Review Service: Manages reviews and ratings for both jobs and companies.
-   Service Registry: Eureka Server for service registration and discovery.
-   Gateway Service: API Gateway responsible for routing requests to the appropriate microservices.

## Technologies Used

-   Spring Framework: The core framework for building Java-based enterprise applications.
-   Spring Cloud: Enables the development of distributed systems and microservices.
-   Eureka Server and Client: Service registry for locating services within the architecture.
-   OpenFeign: Declarative REST client for simplifying inter-service communication.
-   Zipkin: Distributed tracing system for monitoring and troubleshooting.
-   Micrometer: Metrics collection framework for monitoring application performance.
-   API Gateway: Handles API routing and acts as an entry point for external clients.
-   Maven: Build tool for managing project dependencies and building the application.

## Prerequisites

Ensure the following prerequisites are met before running the application:

-   Java 17 or later installed
-   Docker and Docker Compose for managing PostgreSQL instances

## Setup

1.  Clone the repository:
    
    `git clone https://github.com/your-username/job-portal-microservice.git`
    `cd job-portal-microservice` 
    
2.  Build and run the microservices:
  
    `mvn clean install && docker-compose up --build` 
    
3.  Access the services:
    
    -   Eureka Dashboard: http://localhost:8761
    -   Zipkin Dashboard: http://localhost:9411

## Usage

### Job Service

The Job Service microservice handles job-related functionalities such as creating, updating, and retrieving job postings. Users or developers can interact with the following endpoints:
  - **GET /jobs:**  Retrieve all job listings.
  - **GET /jobs/{id}:**  Retrieve a specific job listing by ID.
  - **POST /jobs:**  Create a new job listing.
  - **PUT /jobs/{id}:**  Update a job listing.
  - **DELETE /jobs/{id}:**  Delete a job listing.

### Company Service

The Company Service microservice manages company-related operations, including company profiles and details. Users or developers can interact with the following endpoints:
 - **GET /companies/{companyId}:**  Retrieve all companies.
 - **GET /companies/{companyId}:**  Retrieve a specific company by ID.
 - **POST /companies:**  Create a new company.
 - **PUT /companies/{companyId}:**  Update a company's information.
 - **DELETE /companies/{companyId}:**  Delete a company.

### Review Service

The Review Service microservice manages reviews and ratings for both jobs and companies. Users or developers can interact with the following endpoints:
  - **GET /reviews?{companyId}**  Retrieve all reviews for a specific company.
  - **GET /reviews/{reviewId}**  Retrieve a specific review by ID.
  - **POST /reviews/?{companyId}**  Submit a review for a company.
  - **PUT /reviews/{reviewId}?{companyId}:**  Update a review.
  - **DELETE /reviews/{reviewId}:**  Delete a review by ID.

## Contributing

If you'd like to contribute to the project, please follow the standard GitHub Fork and Pull Request workflow.

## License

This project is licensed under the MIT License.
