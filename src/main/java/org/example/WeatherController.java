package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping("/status")
    public Map<String, String> status() {
        return Map.of("status", "ok");
    }
    @GetMapping
    public ResponseEntity<?> getCurrent(@RequestParam String city) {
        try {
            return ResponseEntity.ok(service.getWeatherInfo(city.trim()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Cannot fetch current weather for: " + city + " | " + e.getMessage());
        }
    }
    @GetMapping("/forecast")
    public ResponseEntity<?> getForecast(
            @RequestParam String city,
            @RequestParam(defaultValue = "3") int days
    ) {
        try {
            return ResponseEntity.ok(service.getForecast(city.trim(), days));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Cannot fetch forecast for: " + city + " | " + e.getMessage());
        }
    }

}
