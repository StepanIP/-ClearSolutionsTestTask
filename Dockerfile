FROM openjdk:17
COPY build/libs/Clear_Solutions-0.0.1-SNAPSHOT.jar app.jar
CMD ["java","-jar","app.jar"]