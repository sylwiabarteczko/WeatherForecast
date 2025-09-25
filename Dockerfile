FROM openjdk:21-slim
COPY target/WeatherForecast-1.0.0.jar /plik.jar
WORKDIR /
ENTRYPOINT ["java","-jar","/plik.jar"]