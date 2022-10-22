FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} stove-api.jar
ENTRYPOINT ["java","-jar","/stove-api.jar"]