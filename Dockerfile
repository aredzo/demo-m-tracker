FROM amazoncorretto:8
COPY target/m-tracker-*.jar /opt/m-tracker.jar
EXPOSE 8080/tcp
CMD ["java", "-jar", "/opt/m-tracker.jar"]
