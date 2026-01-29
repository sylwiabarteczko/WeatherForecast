FROM openjdk:21-slim
WORKDIR /
COPY target/WeatherForecast-1.0.0.jar /plik.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/plik.jar"]