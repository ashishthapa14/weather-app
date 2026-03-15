package com.weather.api.controller;

import com.weather.api.dto.WeatherData;
import com.weather.api.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@Tag(name = "Weather API", description = "Endpoints for fetching weather data")
@CrossOrigin(origins = "*") // Allow frontend access
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current")
    @Operation(summary = "Get current weather by city name")
    public ResponseEntity<WeatherData> getCurrentWeather(
            @Parameter(description = "City name to fetch weather for", required = true) 
            @RequestParam String city) {
        
        WeatherData data = weatherService.getCurrentWeather(city);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/suggestions")
    @Operation(summary = "Get autocomplete city suggestions")
    public ResponseEntity<com.weather.api.dto.CitySuggestion[]> getCitySuggestions(
            @Parameter(description = "Partial city name to search for", required = true)
            @RequestParam String query) {
        
        com.weather.api.dto.CitySuggestion[] suggestions = weatherService.getCitySuggestions(query);
        return ResponseEntity.ok(suggestions);
    }
}
