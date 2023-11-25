## Use an official Maven image as the base image
FROM maven:3.8.7-openjdk-18-slim AS builder
## Set the working directory in the container
WORKDIR /app
## Copy the pom.xml and the project files to the container
COPY pom.xml .
## Download dependencies
#RUN mvn dependency:go-offline -B
## Copy the source code
COPY src ./src
## Build the application using Maven
RUN mvn clean package -DskipTests

## Use an official OpenJDK 21 image as a parent image
FROM openjdk:18-slim
#FROM openjdk:21-jdk

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
#COPY target/*.jar app.jar
COPY --from=builder /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8081

# Specify the command to run on container start
CMD ["java", "-jar", "app.jar"]
