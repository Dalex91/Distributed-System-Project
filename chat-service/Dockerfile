FROM openjdk:19

WORKDIR /app

COPY build/libs/chat-service-all.jar /app/chat-service-all.jar

COPY build/resources/ /app/resources/

EXPOSE 8083

CMD ["java", "-jar", "chat-service-all.jar"]
