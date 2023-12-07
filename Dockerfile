FROM openjdk:17-oracle
COPY target/*.jar texno-bazar.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","txno-bazar.jar"]