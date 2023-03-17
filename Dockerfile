FROM openjdk:19
ADD target/playermaker-0.0.1-SNAPSHOT.jar playermaker.jar
ENTRYPOINT ["java", "-jar", "playermaker.jar"]