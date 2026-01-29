package org.example;

import java.util.List;

public record WeatherInfo(
        String city,
        String temperature,
        String weather,
        String humidity,
        String wind,
        List<HourlyForecast> hourly,
        List<DailyForecast> daily

        ) {
}