FROM gradle:8.7.0-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle :application:adapters:bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/application/adapters/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]