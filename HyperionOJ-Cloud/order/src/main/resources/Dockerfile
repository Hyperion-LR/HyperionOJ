FROM java:8
VOLUME /tmp
ADD order-1.0-SNAPSHOT.jar app.jar
EXPOSE 8888
ENTRYPOINT [ "java", "-jar", "/app.jar" ]