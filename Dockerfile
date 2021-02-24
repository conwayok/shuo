FROM openjdk:8-jre-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8081
CMD ["java", "-jar", "/app.jar"]
