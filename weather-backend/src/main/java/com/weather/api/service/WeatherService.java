package com.weather.api.service;

import com.weather.api.dto.OpenWeatherResponse;
import com.weather.api.dto.WeatherData;
import com.weather.api.exception.WeatherException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.base-url}")
    private String baseUrl;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherData getCurrentWeather(String city) {
        if ("mock_key".equals(apiKey)) {
            return getMockWeather(city);
        }

        String url = String.format("%s/weather?q=%s&appid=%s&units=metric", baseUrl, city, apiKey);
        
        try {
            OpenWeatherResponse response = restTemplate.getForObject(url, OpenWeatherResponse.class);
            return mapToWeatherData(response);
        } catch (Exception e) {
            throw new WeatherException("Failed to fetch weather data for city: " + city, e);
        }
    }

    public com.weather.api.dto.CitySuggestion[] getCitySuggestions(String query) {
        if ("mock_key".equals(apiKey) || query == null || query.trim().length() < 3) {
            return new com.weather.api.dto.CitySuggestion[0];
        }

        String url = String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=5&appid=%s", query, apiKey);
        
        try {
            return restTemplate.getForObject(url, com.weather.api.dto.CitySuggestion[].class);
        } catch (Exception e) {
            System.err.println("Failed to fetch city suggestions: " + e.getMessage());
            return new com.weather.api.dto.CitySuggestion[0];
        }
    }

    private WeatherData mapToWeatherData(OpenWeatherResponse response) {
        if (response == null || response.getWeather() == null || response.getWeather().isEmpty()) {
            throw new WeatherException("Invalid weather data received from API");
        }

        WeatherData data = new WeatherData();
        data.setCity(response.getName());
        
        if (response.getSys() != null) {
            data.setCountry(response.getSys().getCountry());
            data.setSunrise(response.getSys().getSunrise());
            data.setSunset(response.getSys().getSunset());
        }

        if (response.getMain() != null) {
            data.setTemperature(response.getMain().getTemp());
            data.setFeelsLike(response.getMain().getFeels_like());
            data.setHumidity(response.getMain().getHumidity());
        }

        if (response.getWind() != null) {
            data.setWindSpeed(response.getWind().getSpeed());
        }

        OpenWeatherResponse.Weather weather = response.getWeather().get(0);
        data.setCondition(weather.getMain());
        data.setDescription(weather.getDescription());
        data.setIcon(weather.getIcon());

        return data;
    }

    private WeatherData getMockWeather(String city) {
        WeatherData data = new WeatherData();
        data.setCity(city);
        data.setCountry("IN");
        data.setTemperature(25.5);
        data.setFeelsLike(26.0);
        data.setHumidity(60);
        data.setWindSpeed(5.5);
        data.setCondition("Clear");
        data.setDescription("clear sky");
        data.setIcon("01d");
        data.setSunrise(System.currentTimeMillis() / 1000 - 3600 * 6);
        data.setSunset(System.currentTimeMillis() / 1000 + 3600 * 6);
        return data;
    }
}
