# Use official OpenJDK 21 as base image
FROM eclipse-temurin:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/Custom-Application-0.0.1-SNAPSHOT.jar app.jar

# Expose the application's port
EXPOSE 8099

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
