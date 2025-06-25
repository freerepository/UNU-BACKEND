# Use Maven with Java 17
FROM maven:3.8.6-openjdk-17 AS build

WORKDIR /app

# Copy everything and build the JAR
COPY . .
RUN mvn clean package -DskipTests

# Runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose port
ENV PORT=8080
EXPOSE 8080

CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
