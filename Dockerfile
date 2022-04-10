FROM openjdk:17
COPY /.target/corkdork-0.0.1-SNAPSHOT.jar /usr/src/corkdork/
WORKDIR usr/src/corkdork/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "corkdork-0.0.1-SNAPSHOT.jar"]
