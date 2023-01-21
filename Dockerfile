#
# Build stage
#
FROM maven:3.8.7-openjdk-18 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/excel-names-search-0.0.1-SNAPSHOT.jar excel-names-search.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","excel-names-search.jar"]