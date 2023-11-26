
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
   - [Docker](#docker)
     - [Build Image] (#build-image)
     - [Docker Compose](#docker-compose)
     - [Connecting to Kafka](#connecting-to-kafka)
- [Usage](#usage)
  - [Creating a Product](#creating-a-product)
- [Tests](#tests)
  - [Unit Tests](#unit-tests)
  - [Integration Tests](#integration-tests)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

### Prerequisites

- Java 18: [Download](https://openjdk.java.net/projects/jdk/18/)
- Apache Kafka 2.8.1: [Download](https://kafka.apache.org/downloads)
- MySQL: [Download](https://www.mysql.com/downloads/)

### Installation

1. Clone the repository:

   ```bash
   git clone git@github.com:bodzi/interview_project.git
   cd interview_project
   ```

2. Build and run the project:

   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```


#### Docker

Docker support is available for running and testing the service in containers.


#### Build Image

Building of image will also build project so you do not need to have Java 18 on your machine.

```bash
docker build -t product-service .
```

#### Docker Compose

Use the provided `docker-compose.yml` file to run MySQL, Kafka, and the Spring Boot service together:

```bash
docker-compose up -d
```

This command will start containers for MySQL, Kafka, and the Spring Boot service in the background.

To stop the containers:

```bash
docker-compose down
```
#### Connecting to Kafka

To connect to the Kafka container and verify produced messages, you can use a tool like [Kafkacat](https://github.com/edenhill/kafkacat) or the built-in Kafka console tools.

#### Connecting to Kafka

To connect to the Kafka container and verify produced messages, you can use a tool like [Kafkacat](https://github.com/edenhill/kafkacat) or the built-in Kafka console tools.

Example using Kafkacat:

1. Install Kafkacat:

   ```bash
   # On Linux (Debian/Ubuntu)
   sudo apt-get install kafkacat

   # On macOS (Homebrew)
   brew install kafkacat
   ```

## Usage

#### Creating a Product

To create a product, send a POST request to the endpoint `/product` with the product details in the request body.

Example using cURL:

```bash
curl -X POST \
  http://localhost:8081/product \
  -H 'Content-Type: application/json' \
  -d '{
    "name": "Sample Product",
    "price": 19.99
  }'
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


## Contributing

If you would like to contribute to this project, please fork the repository, make your changes, and submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

