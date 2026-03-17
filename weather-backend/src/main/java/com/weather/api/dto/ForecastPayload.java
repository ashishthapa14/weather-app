package com.weather.api.dto;

import java.util.List;

public class ForecastPayload {
    private List<DailyForecast> daily;
    private List<HourlyForecast> hourly;

    public ForecastPayload() {}

    public ForecastPayload(List<DailyForecast> daily, List<HourlyForecast> hourly) {
        this.daily = daily;
        this.hourly = hourly;
    }

    public List<DailyForecast> getDaily() { return daily; }
    public void setDaily(List<DailyForecast> daily) { this.daily = daily; }

    public List<HourlyForecast> getHourly() { return hourly; }
    public void setHourly(List<HourlyForecast> hourly) { this.hourly = hourly; }
}
