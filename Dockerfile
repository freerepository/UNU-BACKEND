# Stage 1: Build the application using Maven + Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy all files and run build
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the app using lightweight JDK image
FROM eclipse-temurin:17-jdk

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
