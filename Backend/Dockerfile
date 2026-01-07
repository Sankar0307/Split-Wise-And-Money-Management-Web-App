FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy Maven wrapper and pom
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Give execute permission
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Rename jar to fixed name
RUN mv target/*.jar app.jar

EXPOSE 8080

# Run application
CMD ["java", "-jar", "app.jar"]
