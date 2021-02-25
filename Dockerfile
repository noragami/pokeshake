FROM maven:3-jdk-8-alpine AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM gcr.io/distroless/java
COPY --from=build /usr/src/app/target/pokeshake.jar /usr/app/pokeshake.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/pokeshake.jar"]