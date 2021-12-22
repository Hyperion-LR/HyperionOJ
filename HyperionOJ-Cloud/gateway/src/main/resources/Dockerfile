FROM java:8
VOLUME /tmp
ADD gateway-1.0-SNAPSHOT.jar app.jar
EXPOSE 9000
ENTRYPOINT [ "java", "-jar", "/app.jar" ]