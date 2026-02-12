FROM eclipse-temurin:21.0.7_6-jdk-ubi9-minimal
WORKDIR /
COPY target/WeatherForecast-1.0.0.jar /plik.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/plik.jar"]

