package com.weather.api.controller;

import com.weather.api.dto.WeatherData;
import com.weather.api.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.weather.api.dto.ForecastPayload;

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
            @RequestParam("city") String city) {
        
        WeatherData data = weatherService.getCurrentWeather(city);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/forecast")
    @Operation(summary = "Get 5-day and hourly forecast by city name")
    public ResponseEntity<ForecastPayload> getForecast(
            @Parameter(description = "City name to fetch forecast for", required = true) 
            @RequestParam("city") String city) {
        
        ForecastPayload data = weatherService.getFiveDayForecast(city);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/suggestions")
    @Operation(summary = "Get autocomplete city suggestions")
    public ResponseEntity<com.weather.api.dto.CitySuggestion[]> getCitySuggestions(
            @Parameter(description = "Partial city name to search for", required = true)
            @RequestParam("query") String query) {
        
        com.weather.api.dto.CitySuggestion[] suggestions = weatherService.getCitySuggestions(query);
        return ResponseEntity.ok(suggestions);
    }
}
