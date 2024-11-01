FROM openjdk:21-ea-1-jdk-slim
VOLUME /tmp
COPY build/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]