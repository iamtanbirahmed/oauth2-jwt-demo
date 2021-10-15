#
# Build stage
#
FROM maven AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml package
#
# Package stage
#
FROM openjdk:13-jdk-alpine
COPY --from=build /home/app/target/*.jar auth-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","auth-service.jar"]