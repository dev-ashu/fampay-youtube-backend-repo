FROM openjdk:8
ADD target/FampayTest.war FampayTest.war
ADD src/main/resources/application.properties application.properties
EXPOSE 8085
ENTRYPOINT ["java","-jar","FampayTest.war"]