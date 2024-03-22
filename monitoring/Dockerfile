FROM openjdk:19

WORKDIR /app

COPY build/libs/monitoring-all.jar /app/monitoring-all.jar

COPY build/resources/ /app/resources/

EXPOSE 8082

CMD ["java", "-jar", "monitoring-all.jar"]
