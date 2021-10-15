FROM openjdk:11
COPY . ./
ADD target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]