package com.weather.api.dto;

import java.util.List;

public class OpenWeatherForecastResponse {
    
    private List<ForecastData> list;
    private City city;

    public List<ForecastData> getList() { return list; }
    public void setList(List<ForecastData> list) { this.list = list; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }

    public static class ForecastData {
        private long dt;
        private Main main;
        private List<Weather> weather;
        private String dt_txt;

        public long getDt() { return dt; }
        public void setDt(long dt) { this.dt = dt; }

        public Main getMain() { return main; }
        public void setMain(Main main) { this.main = main; }

        public List<Weather> getWeather() { return weather; }
        public void setWeather(List<Weather> weather) { this.weather = weather; }

        public String getDt_txt() { return dt_txt; }
        public void setDt_txt(String dt_txt) { this.dt_txt = dt_txt; }
    }

    public static class Main {
        private double temp;
        private double temp_min;
        private double temp_max;

        public double getTemp() { return temp; }
        public void setTemp(double temp) { this.temp = temp; }

        public double getTemp_min() { return temp_min; }
        public void setTemp_min(double temp_min) { this.temp_min = temp_min; }

        public double getTemp_max() { return temp_max; }
        public void setTemp_max(double temp_max) { this.temp_max = temp_max; }
    }

    public static class Weather {
        private String main;
        private String description;
        private String icon;

        public String getMain() { return main; }
        public void setMain(String main) { this.main = main; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
    }

    public static class City {
        private String name;
        private String country;
        private long sunrise;
        private long sunset;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }

        public long getSunrise() { return sunrise; }
        public void setSunrise(long sunrise) { this.sunrise = sunrise; }

        public long getSunset() { return sunset; }
        public void setSunset(long sunset) { this.sunset = sunset; }
    }
}
