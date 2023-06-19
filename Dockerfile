# Build stage
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -Dmaven.test.skip=true -f /home/app/pom.xml install package

# Package stage
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/patterns-api-0.0.1-SNAPSHOT.jar /usr/local/lib/patterns-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=production", "-jar","/usr/local/lib/patterns-api.jar"]