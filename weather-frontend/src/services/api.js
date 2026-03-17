const API_BASE_URL = 'http://localhost:8080/api/v1/weather';

export const fetchCurrentWeather = async (city) => {
  try {
    const response = await fetch(`${API_BASE_URL}/current?city=${encodeURIComponent(city)}`);
    if (!response.ok) {
      throw new Error('Failed to fetch weather data');
    }
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const fetchForecast = async (city) => {
  try {
    const response = await fetch(`${API_BASE_URL}/forecast?city=${encodeURIComponent(city)}`);
    if (!response.ok) {
      throw new Error('Failed to fetch forecast data');
    }
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const fetchAIInsights = async (city, query) => {
  try {
    const response = await fetch(`${API_BASE_URL}/ai/insights?city=${encodeURIComponent(city)}&query=${encodeURIComponent(query)}`);
    if (!response.ok) {
      throw new Error('Failed to fetch AI insights');
    }
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const fetchCitySuggestions = async (query) => {
  try {
    const response = await fetch(`${API_BASE_URL}/suggestions?query=${encodeURIComponent(query)}`);
    if (!response.ok) {
      throw new Error('Failed to fetch suggestions');
    }
    return await response.json();
  } catch (error) {
    console.error(error);
    return [];
  }
};
