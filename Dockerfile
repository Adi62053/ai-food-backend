# -----------------------------
# Stage 1: Build
# -----------------------------
FROM maven:3.9.11-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies (cached)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build JAR
RUN mvn clean package -DskipTests

# -----------------------------
# Stage 2: Run
# -----------------------------
FROM eclipse-temurin:17-jre-ubi9-minimal

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
