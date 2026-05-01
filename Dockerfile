FROM gradle:9.4.1-jdk17-ubi9 AS build

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew gradlew.bat ./
COPY src ./src

RUN gradle clean bootJar --no-daemon

FROM eclipse-temurin:17.0.18_8-jre-jammy

WORKDIR /app

COPY --from=build /app/build/libs/payment-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java","-jar","/app/app.jar"]
