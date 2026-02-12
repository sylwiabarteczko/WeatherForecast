package org.example;

import java.util.List;

public class WeatherMapper {

    public static WeatherInfo toWeatherInfo(WeatherApiResponseDTO dto) {

        if (dto.error != null) {
            throw new RuntimeException(
                    "WeatherAPI error " + dto.error.code + ": " + dto.error.message
            );
        }

        String city = dto.location.name;

        List<HourlyForecast> hourly = dto.forecast.forecastday.get(0).hour
                .stream()
                .limit(12)
                .map(h -> new HourlyForecast(
                        h.time,
                        h.tempC,
                        h.condition.text,
                        h.chanceOfRain == null ? "0" : h.chanceOfRain
                ))
                .toList();

        List<DailyForecast> daily = dto.forecast.forecastday
                .stream()
                .map(d -> new DailyForecast(
                        d.date,
                        d.day.minTempC,
                        d.day.maxTempC,
                        d.day.condition.text,
                        d.day.dailyChanceOfRain == null ? "0" : d.day.dailyChanceOfRain
                ))
                .toList();

        return new WeatherInfo(
                city,
                dto.current.tempC,
                dto.current.condition.text,
                dto.current.humidity,
                dto.current.windKph,
                hourly,
                daily
        );
    }
    public static WeatherInfo toCurrentWeatherInfo(WeatherApiResponseDTO dto) {

        if (dto.error != null) {
            throw new RuntimeException(
                    "WeatherAPI error " + dto.error.code + ": " + dto.error.message
            );
        }

        return new WeatherInfo(
                dto.location.name,
                dto.current.tempC,
                dto.current.condition.text,
                dto.current.humidity,
                dto.current.windKph,
                List.of(),
                List.of()
        );
    }

}

