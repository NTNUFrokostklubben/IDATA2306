#Dockerfile
# Use a base image with Java and Maven
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copy only the POM file first to leverage Docker cache for dependencies
COPY pom.xml .
# Download dependencies (this layer is cached unless pom.xml changes)
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application (this layer will re-run only if src/ or pom.xml changes)
RUN mvn package -DskipTests

# Use a lightweight JRE image for runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your Spring app runs on and debug port 5005
EXPOSE 8080
EXPOSE 5005

# Run the application
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "app.jar"]