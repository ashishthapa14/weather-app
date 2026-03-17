package com.weather.api.controller;

import com.weather.api.dto.AIResponse;
import com.weather.api.service.WeatherAIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather/ai")
@Tag(name = "Weather AI Assistant", description = "Endpoints for natural language weather queries")
@CrossOrigin(origins = "*")
public class WeatherAIController {

    private final WeatherAIService weatherAIService;

    public WeatherAIController(WeatherAIService weatherAIService) {
        this.weatherAIService = weatherAIService;
    }

    @GetMapping("/insights")
    @Operation(summary = "Get AI-powered weather insights for a city")
    public ResponseEntity<AIResponse> getInsights(
            @Parameter(description = "City name to check weather for", required = true)
            @RequestParam("city") String city,
            @Parameter(description = "Natural language question (e.g., Do I need an umbrella?)", required = true)
            @RequestParam("query") String query) {

        AIResponse response = weatherAIService.getInsights(city, query);
        return ResponseEntity.ok(response);
    }
}
