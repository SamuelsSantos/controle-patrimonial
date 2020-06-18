FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_PATH
COPY $JAR_PATH app.jar

ENTRYPOINT java -jar app.jar