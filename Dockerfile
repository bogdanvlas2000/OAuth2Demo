FROM openjdk:11
WORKDIR /usr/src/app
COPY .  ./
ADD target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]