FROM openjdk:17-slim AS build
WORKDIR /usr/src/app

COPY pom.xml ./pom.xml
COPY mvnw ./mvnw
COPY mvnw.cmd ./mvnw.cmd
COPY .mvn ./.mvn
COPY src ./src

RUN apt-get update && apt-get install dos2unix
RUN chmod +x mvnw
RUN dos2unix mvnw && ./mvnw clean install -DskipTests

FROM openjdk:17-slim AS runtime
WORKDIR /usr/src/app

COPY --from=build /usr/src/app/target/colombina-*.jar java-app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "java-app.jar"]
