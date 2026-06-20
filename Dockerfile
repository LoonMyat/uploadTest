# FROM eclipse-temurin:25-jdk

# WORKDIR /app

# COPY . .

# RUN chmod +x mvnw
# RUN ./mvnw clean package -DskipTests

# EXPOSE 8080

# CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar target/*.jar"]

# 1. Build stage
FROM maven:3.9.12-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# 2. Run stage
FROM eclipse-temurin:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]