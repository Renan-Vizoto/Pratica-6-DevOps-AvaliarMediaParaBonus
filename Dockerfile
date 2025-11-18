# syntax=docker/dockerfile:1

##
## Build stage - package the Spring Boot jar skipping tests
##
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /workspace

COPY pom.xml .
RUN mvn -B -ntp dependency:go-offline

COPY src src
RUN mvn -B -ntp -DskipTests clean package

##
## Runtime stage - run the packaged Spring Boot app
##
FROM eclipse-temurin:21-jre-alpine

ENV APP_HOME=/app \
    JAVA_OPTS="" \
    SPRING_PROFILES_ACTIVE=default

WORKDIR ${APP_HOME}

COPY --from=build /workspace/target/avaliar-media-para-bonus-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
