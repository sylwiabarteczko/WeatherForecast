package org.example;

public record WeatherInfo(
        String city,
        String temperature,
        String weather,
        String humidity,
        String wind) {

}
