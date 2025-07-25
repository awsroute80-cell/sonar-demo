#Use a lightweight JDK base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built jar from Maven target folder
COPY target/sonar-demo-1.0-SNAPSHOT.jar app.jar

# Expose port 8081 for the Jetty server
EXPOSE 8081

# Run the app
CMD ["java", "-jar", "app.jar"]
