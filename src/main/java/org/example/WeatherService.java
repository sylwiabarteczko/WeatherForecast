package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class WeatherService {

    private static final String API_KEY = "744312a178b94edaacb32247252706";
    private static final String BASE_URL = "https://api.weatherapi.com/v1";
    private final ObjectMapper mapper = new ObjectMapper();

    public WeatherInfo getWeatherInfo(String cityName) throws IOException {
        String json = fetchCurrentJson(cityName);
        JsonNode root = mapper.readTree(json);

        ensureNoApiError(root);

        String city = root.get("location").get("name").asText();
        JsonNode current = root.path("current");

        String condition = current.path("condition").path("text").asText();
        String temperature = current.path("temp_c").asText();
        String humidity = current.path("humidity").asText();
        String wind = current.path("wind_kph").asText();

        return new WeatherInfo(city, temperature, condition, humidity, wind, List.of(), List.of());
    }

    public WeatherInfo getForecast(String cityName, int days) throws IOException {
        if (days < 1) days = 1;
        if (days > 10) days = 10;

        String json = fetchForecastJson(cityName, days);
        JsonNode root = mapper.readTree(json);

        ensureNoApiError(root);

        String city = root.path("location").path("name").asText();

        JsonNode current = root.path("current");
        String condition = current.path("condition").path("text").asText();
        String temperature = current.path("temp_c").asText();
        String humidity = current.path("humidity").asText();
        String wind = current.path("wind_kph").asText();

        List<HourlyForecast> hourly = new ArrayList<>();
        JsonNode hours = root.path("forecast").path("forecastday").path(0).path("hour");

        int limit = Math.min(hours.size(), 12);
        for (int i = 0; i < limit; i++) {
            JsonNode h = hours.path(i);
            hourly.add(new HourlyForecast(
                    h.path("time").asText(),
                    h.path("temp_c").asText(),
                    h.path("condition").path("text").asText(),
                    h.path("chance_of_rain").asText("0")
            ));
        }

        List<DailyForecast> daily = new ArrayList<>();
        JsonNode daysArr = root.path("forecast").path("forecastday");
        for (int i = 0; i < daysArr.size(); i++) {
            JsonNode d = daysArr.path(i);
            JsonNode day = d.path("day");
            daily.add(new DailyForecast(
                    d.path("date").asText(),
                    day.path("mintemp_c").asText(),
                    day.path("maxtemp_c").asText(),
                    day.path("condition").path("text").asText(),
                    day.path("daily_chance_of_rain").asText("0")
            ));
        }

        return new WeatherInfo(city, temperature, condition, humidity, wind, hourly, daily);
    }
    private String fetchCurrentJson(String cityName) throws IOException {
        String q = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        String urlString = BASE_URL + "/current.json?key=" + API_KEY + "&q=" + q;
        return httpGet(urlString);
    }
    private String fetchForecastJson(String cityName, int days) throws IOException {
        String q = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        String urlString = BASE_URL + "/forecast.json?key=" + API_KEY + "&q=" + q + "&days=" + days;
        return httpGet(urlString);
    }
    private String httpGet(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int code = conn.getResponseCode();
        Scanner scanner = new Scanner(
                (code >= 400) ? conn.getErrorStream() : conn.getInputStream()
        );

        StringBuilder result = new StringBuilder();
        while (scanner.hasNextLine()) {
            result.append(scanner.nextLine());
        }
        scanner.close();

        if (code >= 400) {
            throw new IOException("WeatherAPI HTTP " + code + " -> " + result);
        }
        return result.toString();
    }
    private void ensureNoApiError(JsonNode root) throws IOException {

        JsonNode err = root.path("error");
        if (!err.isMissingNode() && !err.isNull()) {
            String msg = err.path("message").asText("Unknown API error");
            int code = err.path("code").asInt(-1);
            throw new IOException("WeatherAPI error " + code + ": " + msg);
        }
    }
}
