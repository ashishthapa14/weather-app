package com.weather.api.dto;

public class DailyForecast {
    private long date;
    private double minTemp;
    private double maxTemp;
    private String condition;
    private String description;
    private String icon;

    public DailyForecast() {}

    public long getDate() { return date; }
    public void setDate(long date) { this.date = date; }

    public double getMinTemp() { return minTemp; }
    public void setMinTemp(double minTemp) { this.minTemp = minTemp; }

    public double getMaxTemp() { return maxTemp; }
    public void setMaxTemp(double maxTemp) { this.maxTemp = maxTemp; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}
