package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeatherApiResponseDTO {

    public Location location;
    public Current current;
    public Forecast forecast;
    public Error error;

    public static class Location {
        public String name;
    }

    public static class Current {
        @JsonProperty("temp_c")
        public String tempC;
        public String humidity;
        @JsonProperty("wind_kph")
        public String windKph;
        public Condition condition;
    }

    public static class Condition {
        public String text;
    }

    public static class Forecast {
        public List<ForecastDay> forecastday;
    }

    public static class ForecastDay {
        public String date;
        public Day day;
        public List<Hour> hour;
    }

    public static class Day {
        @JsonProperty("mintemp_c")
        public String minTempC;
        @JsonProperty("maxtemp_c")
        public String maxTempC;
        @JsonProperty("daily_chance_of_rain")
        public String dailyChanceOfRain;
        public Condition condition;
    }

    public static class Hour {
        public String time;
        @JsonProperty("temp_c")
        public String tempC;
        @JsonProperty("chance_of_rain")
        public String chanceOfRain;
        public Condition condition;
    }

    public static class Error {
        public int code;
        public String message;
    }
}
