# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml to leverage caching
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Make mvnw executable
RUN chmod +x ./mvnw

# Copy the source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Copy the built JAR file to the working directory
COPY target/*.jar app.jar

# Expose the port your app runs on (matches server.port)
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]