FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} addressbook-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/addressbook-0.0.1-SNAPSHOT.jar"]
EXPOSE  8181
