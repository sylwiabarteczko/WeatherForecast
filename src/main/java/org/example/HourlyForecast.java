package org.example;

public record HourlyForecast(
        String time,
        String tempC,
        String condition,
        String chanceOfRain
) {
}
