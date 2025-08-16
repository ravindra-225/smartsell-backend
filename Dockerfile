# ===== Build Stage =====
FROM openjdk:17-jdk-slim AS builder

WORKDIR /app

# Copy Maven files first for caching
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Make wrapper executable
RUN chmod +x ./mvnw

# Download dependencies (to cache layers)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# ===== Runtime Stage =====
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the jar from builder
COPY --from=builder /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
