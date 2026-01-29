package org.example;

public record DailyForecast(
        String date,
        String minTempC,
        String maxTempC,
        String condition,
        String chanceOfRain
) {
}
