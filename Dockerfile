FROM amazoncorretto:8
EXPOSE 8081
COPY ./target/*.jar Autologin.jar
ENTRYPOINT ["java","-jar","Autologin.jar"]