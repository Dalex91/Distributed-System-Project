FROM openjdk:11-jre-slim

WORKDIR /app

COPY build/libs/device-server-all.jar /app/device-server-all.jar

COPY build/resources/ /app/resources/

EXPOSE 8080

CMD ["java", "-jar", "device-server-all.jar"]
