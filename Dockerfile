FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
COPY . .



RUN mvn clean package -DskipTests

FROM openjdk:21-slim
COPY --from=build /target/evote-0.0.1-SNAPSHOT.jar /target/evote-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/target/evote-0.0.1-SNAPSHOT.jar"]
