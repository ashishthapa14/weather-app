package com.weather.api.dto;

import java.util.List;

public class OpenWeatherAirPollutionResponse {

    private List<PollutionData> list;

    public List<PollutionData> getList() { return list; }
    public void setList(List<PollutionData> list) { this.list = list; }

    public static class PollutionData {
        private Main main;

        public Main getMain() { return main; }
        public void setMain(Main main) { this.main = main; }
    }

    public static class Main {
        private int aqi;

        public int getAqi() { return aqi; }
        public void setAqi(int aqi) { this.aqi = aqi; }
    }
}
