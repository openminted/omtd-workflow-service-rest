FROM openjdk:9-jre


WORKDIR /
ADD ./omtd-workflow-service-rest-server/target/omtd-workflow-service-rest-server-0.0.1-SNAPSHOT.jar app.jar
ADD ./scripts/application.properties application.properties

CMD java -jar app.jar --spring.config.location=application.properties
