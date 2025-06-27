package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherService {

    private static final String API_KEY = "744312a178b94edaacb32247252706";
    public WeatherInfo getWeatherInfo(String cityName) throws IOException {
        String json = fetchJson(cityName);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        String city = root.get("location").get("name").asText();
        String condition = root.get("current").get("condition").get("text").asText();
        String temperature = root.get("current").get("temp_c").asText();
        String humidity = root.get("current").get("humidity").asText();
        String wind = root.get("current").get("wind_kph").asText();

        return new WeatherInfo(city, temperature, condition, humidity, wind);
    }

    private String fetchJson(String cityName) throws IOException {
        String urlString = String.format(
                "http://api.weatherapi.com/v1/current.json?key=%s&q=%s",
                API_KEY, cityName
        );
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        Scanner scanner = new Scanner(conn.getInputStream());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()) {
            result.append(scanner.nextLine());
        }
        scanner.close();
        return result.toString();
    }

}
