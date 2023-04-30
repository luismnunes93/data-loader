FROM maven:3.8.5-openjdk-17 AS maven
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17-jdk-alpine
COPY --from=maven /home/app/target/data-loader-1.0.0.jar challenge-1.0.0.jar
ENTRYPOINT ["java", "-jar","challenge-1.0.0.jar"]
EXPOSE 8080
