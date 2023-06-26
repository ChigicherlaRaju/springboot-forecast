FROM openjdk:11-jre-slim
EXPOSE 8080
ADD target/springboot-forecast.jar springboot-forecast.jar
ENTRYPOINT [ "java", "-jar", "/springboot-forecast.jar" ]
