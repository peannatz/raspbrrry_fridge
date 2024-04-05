
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests -X

FROM openjdk:17
EXPOSE 9001
COPY --from=build target/RaspbrrryFridge-0.0.1-SNAPSHOT.jar RaspbrrryFridge.jar
ENTRYPOINT ["java","-jar","/RaspbrrryFridge.jar"]
