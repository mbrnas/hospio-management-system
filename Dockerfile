FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .

COPY src src

RUN mvn clean package

FROM openjdk:17-slim

WORKDIR /app

COPY target/hospital-management-system.jar /app/application.jar

CMD ["java", "-jar", "application.jar"]

EXPOSE 7070
