FROM maven:3.8.3-adoptopenjdk-11 AS maven
COPY pom.xml ./pom.xml
COPY src ./src
RUN mvn package

FROM adoptopenjdk:11 AS run
COPY --from=maven target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]