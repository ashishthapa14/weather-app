package com.weather.api.dto;

import java.util.List;

public class OpenWeatherResponse {
    
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private Sys sys;
    private String name;

    // Getters and Setters
    public List<Weather> getWeather() { return weather; }
    public void setWeather(List<Weather> weather) { this.weather = weather; }

    public Main getMain() { return main; }
    public void setMain(Main main) { this.main = main; }

    public Wind getWind() { return wind; }
    public void setWind(Wind wind) { this.wind = wind; }

    public Sys getSys() { return sys; }
    public void setSys(Sys sys) { this.sys = sys; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public static class Weather {
        private String main;
        private String description;
        private String icon;

        // Getters and Setters
        public String getMain() { return main; }
        public void setMain(String main) { this.main = main; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
    }

    public static class Main {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int humidity;

        // Getters and Setters
        public double getTemp() { return temp; }
        public void setTemp(double temp) { this.temp = temp; }
        public double getFeels_like() { return feels_like; }
        public void setFeels_like(double feels_like) { this.feels_like = feels_like; }
        public double getTemp_min() { return temp_min; }
        public void setTemp_min(double temp_min) { this.temp_min = temp_min; }
        public double getTemp_max() { return temp_max; }
        public void setTemp_max(double temp_max) { this.temp_max = temp_max; }
        public int getHumidity() { return humidity; }
        public void setHumidity(int humidity) { this.humidity = humidity; }
    }

    public static class Wind {
        private double speed;

        // Getters and Setters
        public double getSpeed() { return speed; }
        public void setSpeed(double speed) { this.speed = speed; }
    }

    public static class Sys {
        private String country;
        private long sunrise;
        private long sunset;

        // Getters and Setters
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public long getSunrise() { return sunrise; }
        public void setSunrise(long sunrise) { this.sunrise = sunrise; }
        public long getSunset() { return sunset; }
        public void setSunset(long sunset) { this.sunset = sunset; }
    }
}
