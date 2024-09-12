FROM amazoncorretto:17.0.7-alpine
COPY build/libs/server-0.0.1-SNAPSHOT.jar triples.jar
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "triples.jar"]