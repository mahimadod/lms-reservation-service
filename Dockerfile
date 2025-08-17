## Use an official Java runtime as a base image
#FROM openjdk:21-jdk-slim
#
## Set the working directory inside the container
#WORKDIR /app
#
## Copy the JAR file into the container
#COPY target/authenticator-service-0.0.1-SNAPSHOT.jar app.jar
#
## Expose the Eureka default port
#EXPOSE 8761
#
## Run the JAR file
#ENTRYPOINT ["java", "-jar", "app.jar"]
FROM openjdk:21-jdk-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]