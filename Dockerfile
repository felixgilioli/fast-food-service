FROM gradle:8.7.0-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle :camadas:infrastructure:bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/camadas/infrastructure/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]