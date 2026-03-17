package com.weather.api.dto;

public class HourlyForecast {
    private long timestamp;
    private double temp;
    private String icon;

    public HourlyForecast() {}

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public double getTemp() { return temp; }
    public void setTemp(double temp) { this.temp = temp; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}
