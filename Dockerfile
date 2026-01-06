# syntax=docker/dockerfile:1

FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copia apenas o necessário para resolver dependências e aproveitar cache
COPY gradlew gradlew.bat settings.gradle.kts build.gradle.kts ./
COPY gradle ./gradle
COPY camadas/*/build.gradle.kts ./camadas/

RUN chmod +x ./gradlew

# Baixa dependências (sem falhar por dependency verification no container)
RUN ./gradlew --no-daemon -Dorg.gradle.dependency.verification=off dependencies

# Agora copia o código
COPY . .

# Em alguns ambientes (ex: buildx) o bit executável do gradlew pode se perder após o COPY
RUN chmod +x ./gradlew

# Build do jar
RUN ./gradlew :camadas:infrastructure:bootJar --no-daemon -Dorg.gradle.dependency.verification=off

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/camadas/infrastructure/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]