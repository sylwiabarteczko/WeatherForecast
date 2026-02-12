package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Service
public class WeatherService {

    private static final String API_KEY = "744312a178b94edaacb32247252706";
    private static final String BASE_URL = "https://api.weatherapi.com/v1";
    private final ObjectMapper mapper = new ObjectMapper();

    public WeatherInfo getWeatherInfo(String cityName) throws IOException {
        String json = fetchCurrentJson(cityName);

        WeatherApiResponseDTO dto =
                mapper.readValue(json, WeatherApiResponseDTO.class);

        return WeatherMapper.toCurrentWeatherInfo(dto);
    }


    public WeatherInfo getForecast(String cityName, int days) throws IOException {
        if (days < 1) days = 1;
        if (days > 10) days = 10;

        String json = fetchForecastJson(cityName, days);

        WeatherApiResponseDTO dto =
                mapper.readValue(json, WeatherApiResponseDTO.class);

        return WeatherMapper.toWeatherInfo(dto);
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

}
