package org.example;

import java.io.IOException;
import java.util.Scanner;

public class WeatherApp {
    private final WeatherService weatherService = new WeatherService();

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("City name: ");
        String city = scanner.nextLine().trim();

        try {
            WeatherInfo info = weatherService.getWeatherInfo(city);
            if (info != null) {
                printWeather(info);
            } else {
                System.out.println("No information about city: " + city);
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    private void printWeather(WeatherInfo info) {
        System.out.println("\n🌤️ Weather in a city: " + info.city());
        System.out.println("Temperature: " + info.temperature() + "°C");
        System.out.println("Condition: " + info.weather());
        System.out.println("Humidity: " + info.humidity() + "%");
        System.out.println("Wind speed: " + info.wind() + " km/h");
    }

}
