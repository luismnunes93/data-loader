FROM openjdk:17-jdk-alpine
ADD target/data-loader-1.0.0.jar challenge-1.0.0.jar
ENTRYPOINT ["java", "-jar","challenge-1.0.0.jar"]
EXPOSE 8080
