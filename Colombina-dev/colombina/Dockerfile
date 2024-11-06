FROM maven:3.8.1-jdk-11 AS build
WORKDIR /usr/src/app

COPY src ./src
COPY pom.xml ./pom.xml

RUN mvn clean package

FROM openjdk:17-jdk-alpine AS runtime
WORKDIR /usr/src/app

COPY --from=build /usr/src/app/target/colombina-*.jar java-app.jar

ENTRYPOINT [ "java", "-jar", "java-app.jar"]
