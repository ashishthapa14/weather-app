package com.weather.api.dto;

public class AIResponse {
    private String query;
    private String aiInsight;

    public AIResponse() {}

    public AIResponse(String query, String aiInsight) {
        this.query = query;
        this.aiInsight = aiInsight;
    }

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
    public String getAiInsight() { return aiInsight; }
    public void setAiInsight(String aiInsight) { this.aiInsight = aiInsight; }
}
