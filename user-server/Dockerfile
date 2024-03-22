FROM openjdk:11-jre-slim

WORKDIR /app

COPY build/libs/user.example.user-server-all.jar /app/user.example.user-server-all.jar

COPY build/resources/ /app/resources/

EXPOSE 8081

CMD ["java", "-jar", "user.example.user-server-all.jar"]
