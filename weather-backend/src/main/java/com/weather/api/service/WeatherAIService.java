package com.weather.api.service;

import com.weather.api.dto.AIResponse;
import com.weather.api.dto.WeatherData;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class WeatherAIService {

    private final ChatClient chatClient;
    private final WeatherService weatherService;

    public WeatherAIService(ChatModel chatModel, WeatherService weatherService) {
        this.chatClient = ChatClient.create(chatModel);
        this.weatherService = weatherService;
    }

    public AIResponse getInsights(String city, String query) {
        try {
            WeatherData weather = weatherService.getCurrentWeather(city);
            
            String context = String.format("Current weather in %s is %s with a temperature of %.1f°C. Humidity is %d%%.", 
                weather.getCity(), weather.getDescription(), weather.getTemperature(), weather.getHumidity());

            String systemPrompt = "You are a helpful AI weather assistant. Keep your answer brief, friendly, and directly address the user's question based on the provided weather context. Include practical advice when relevant.";
            
            String userPrompt = String.format("Context: %s\nQuestion: %s", context, query);

            String aiInsight = chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .content();

            return new AIResponse(query, aiInsight);
                
        } catch (Exception e) {
            // Fallback gracefully if AI is unreachable or not configured
            return new AIResponse(query, "I'm having trouble analyzing the weather right now, but you can check the standard forecast on our dashboard!");
        }
    }
}
