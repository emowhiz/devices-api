FROM alpine/java:21-jdk AS runtime

WORKDIR /app

COPY target/devices-api-0.0.1-SNAPSHOT.jar /app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/devices-api-0.0.1-SNAPSHOT.jar"]
