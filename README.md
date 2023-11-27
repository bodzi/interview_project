
# Interview Project - Product Service

[![Java](https://img.shields.io/badge/Java-18-blue)](https://openjdk.java.net/projects/jdk/18/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-green)](https://spring.io/projects/spring-boot)
[![Kafka](https://img.shields.io/badge/Apache%20Kafka-2.8.1-red)](https://kafka.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-latest-orange)](https://www.mysql.com/)

This project is a Spring Boot service that manages products in a distributed system. It includes functionality to create products and communicate changes in near real-time using Apache Kafka.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
    - [Running Locally](#running-locally)
    - [Docker](#docker)
- [Usage](#usage)
  - [Creating a Product](#creating-a-product)
  - [Verify Message](#verify-message)
- [Tests](#tests)
  - [Unit Tests](#unit-tests)
  - [Integration Tests](#integration-tests)


## Getting Started

### Prerequisites

For running on local machine you will need: 

- Java 18: [Download](https://openjdk.java.net/projects/jdk/18/)
- Apache Kafka 2.8.1: [Download](https://kafka.apache.org/downloads)
    - run it on port 9092. create a topic produc-topic
- MySQL: [Download](https://www.mysql.com/downloads/)
    - run in to port 3306. Make a db called productdb with username bojana and pass bojana@123

### Installation

#### Running Locally

1. Clone the repository:

   ```bash
   git clone git@github.com:bodzi/interview_project.git
   cd interview_project
   ```

2. Run the project: 

   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```


### Docker

Docker support is available for running and testing the service in containers.


 1. Build image for product-service:

    ```bash
    docker build -t product-service .
    ```

2. Running containers

    Use the provided `docker-compose.yml` file to run MySQL, Kafka, and the Spring Boot service together:

    ```bash
    docker-compose up -d
    ```

    This command will start containers for MySQL, Kafka, and the Spring Boot service in the background.

    To stop the containers:

    ```bash
    docker-compose down
    ```

## Usage

#### Creating a Product

To create a product, send a POST request to the endpoint `/product` with the product details in the request body.

Example using cURL:

    curl -X POST \
      http://localhost:8081/product \
      -H 'Content-Type: application/json' \
      -d '{
        "name": "Sample Product",
        "price": 19.99
      }'


#### Verify Message

To connect to the Kafka container and verify produced messages, you can use a tool like [Kafkacat](https://github.com/edenhill/kafkacat) or the built-in Kafka console tools.

1. Connect to the Kafka container:

    ```bash
    docker exec -it kafka /bin/sh
    ```
2. Than consume messages from product-topic:

    ```bash
    /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9093 --topic product-topic
    ```
3. Send a product creation request:

    ```bash
    curl -X POST \
      http://localhost:8081/product \
      -H 'Content-Type: application/json' \
      -d '{
        "name": "Product",
        "price": 19.99
      }'
    ```
4. Observe the received message in the Kafkacat terminal.

    Example of received message on broker:

    ```bash
    {"action":"CREATED","product":{"productId":4,"name":"Product","price":19.99}}
    ```


## Tests

### Unit Tests

Run unit tests with:

```bash
./mvnw test
```

### Integration Tests

Run integration tests with:

```bash
./mvnw integration-test
```



