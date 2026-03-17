package com.weather.api.service;

import com.weather.api.dto.DailyForecast;
import com.weather.api.dto.ForecastPayload;
import com.weather.api.dto.HourlyForecast;
import com.weather.api.dto.OpenWeatherForecastResponse;
import com.weather.api.dto.OpenWeatherResponse;
import com.weather.api.dto.WeatherData;
import com.weather.api.exception.WeatherException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        String weatherUrl = String.format("%s/weather?q=%s&appid=%s&units=metric", baseUrl, city, apiKey);
        
        try {
            OpenWeatherResponse response = restTemplate.getForObject(weatherUrl, OpenWeatherResponse.class);
            WeatherData weatherData = mapToWeatherData(response);

            // Fetch AQI and UVI Data using coordinates
            if (response != null && response.getCoord() != null) {
                double lat = response.getCoord().getLat();
                double lon = response.getCoord().getLon();

                // AQI
                try {
                    String aqiUrl = String.format("https://api.openweathermap.org/data/2.5/air_pollution?lat=%s&lon=%s&appid=%s", lat, lon, apiKey);
                    com.weather.api.dto.OpenWeatherAirPollutionResponse aqiResponse = 
                            restTemplate.getForObject(aqiUrl, com.weather.api.dto.OpenWeatherAirPollutionResponse.class);
                    
                    if (aqiResponse != null && aqiResponse.getList() != null && !aqiResponse.getList().isEmpty()) {
                        weatherData.setAqi(aqiResponse.getList().get(0).getMain().getAqi());
                    }
                } catch (Exception e) {
                    System.err.println("Failed to fetch AQI data: " + e.getMessage());
                }

                // UVI
                try {
                    String uviUrl = String.format("https://api.openweathermap.org/data/2.5/uvi?lat=%s&lon=%s&appid=%s", lat, lon, apiKey);
                    com.weather.api.dto.OpenWeatherUviResponse uviResponse = 
                            restTemplate.getForObject(uviUrl, com.weather.api.dto.OpenWeatherUviResponse.class);
                    
                    if (uviResponse != null) {
                        weatherData.setUvi(uviResponse.getValue());
                    }
                } catch (Exception e) {
                    System.err.println("Failed to fetch UVI data: " + e.getMessage());
                }
            }

            return weatherData;
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

        if (response.getCoord() != null) {
            data.setLat(response.getCoord().getLat());
            data.setLon(response.getCoord().getLon());
        }

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
        data.setAqi(2); // Mock AQI: Fair
        return data;
    }

    public ForecastPayload getFiveDayForecast(String city) {
        if ("mock_key".equals(apiKey)) {
            return getMockForecast();
        }

        String url = String.format("%s/forecast?q=%s&appid=%s&units=metric", baseUrl, city, apiKey);
        
        try {
            OpenWeatherForecastResponse response = restTemplate.getForObject(url, OpenWeatherForecastResponse.class);
            if (response == null || response.getList() == null || response.getList().isEmpty()) {
                throw new WeatherException("Invalid forecast data received from API");
            }

            List<HourlyForecast> hourly = new ArrayList<>();
            Map<String, DailyForecast> dailyMap = new HashMap<>();
            
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("UTC"));

            for (int i = 0; i < response.getList().size(); i++) {
                OpenWeatherForecastResponse.ForecastData data = response.getList().get(i);
                
                // Add first 8 items (24 hours) to hourly forecast
                if (i < 8) {
                    HourlyForecast hf = new HourlyForecast();
                    hf.setTimestamp(data.getDt());
                    hf.setTemp(data.getMain().getTemp());
                    if (data.getWeather() != null && !data.getWeather().isEmpty()) {
                        hf.setIcon(data.getWeather().get(0).getIcon());
                    }
                    hourly.add(hf);
                }

                // Aggregate daily min/max
                String dayKey = dateFormatter.format(Instant.ofEpochSecond(data.getDt()));
                DailyForecast df = dailyMap.getOrDefault(dayKey, new DailyForecast());
                
                if (df.getDate() == 0) {
                    df.setDate(data.getDt());
                    df.setMinTemp(data.getMain().getTemp_min());
                    df.setMaxTemp(data.getMain().getTemp_max());
                    if (data.getWeather() != null && !data.getWeather().isEmpty()) {
                        df.setCondition(data.getWeather().get(0).getMain());
                        df.setDescription(data.getWeather().get(0).getDescription());
                        df.setIcon(data.getWeather().get(0).getIcon());
                    }
                } else {
                    if (data.getMain().getTemp_min() < df.getMinTemp()) df.setMinTemp(data.getMain().getTemp_min());
                    if (data.getMain().getTemp_max() > df.getMaxTemp()) df.setMaxTemp(data.getMain().getTemp_max());
                }
                
                dailyMap.put(dayKey, df);
            }

            List<DailyForecast> daily = new ArrayList<>(dailyMap.values());
            // Sort by date (in case keys drifted)
            daily.sort((a, b) -> Long.compare(a.getDate(), b.getDate()));
            
            // Return only up to 5 days
            if (daily.size() > 5) {
                daily = daily.subList(0, 5);
            }

            return new ForecastPayload(daily, hourly);
        } catch (Exception e) {
            throw new WeatherException("Failed to fetch forecast data for city: " + city, e);
        }
    }

    private ForecastPayload getMockForecast() {
        return new ForecastPayload(new ArrayList<>(), new ArrayList<>());
    }
}
