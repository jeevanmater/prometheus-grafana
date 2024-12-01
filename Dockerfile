#FROM ubuntu:latest
#LABEL authors="Jeevan Kumar"
#
#ENTRYPOINT ["top", "-b"]


# Use official OpenJDK image
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven build artifact
COPY target/*.jar app.jar

# Expose port
EXPOSE 7979

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]