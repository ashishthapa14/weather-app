import React from 'react';
import { 
  Cloud, 
  Sun, 
  CloudRain, 
  CloudLightning, 
  CloudDrizzle, 
  Snowflake, 
  CloudFog, 
  Moon,
  CloudSun,
  CloudMoon
} from 'lucide-react';
import './ForecastRow.css';

const ForecastRow = ({ dailyForecasts }) => {
  if (!dailyForecasts || dailyForecasts.length === 0) return null;

  const getDayName = (timestamp) => {
    const date = new Date(timestamp * 1000);
    return date.toLocaleDateString('en-US', { weekday: 'short' });
  };

  const getWeatherIcon = (iconCode) => {
    const iconProps = { className: "forecast-icon", size: 36 };
    
    switch (iconCode) {
      case '01d': return <Sun {...iconProps} color="#f59e0b" />;
      case '01n': return <Moon {...iconProps} color="#6366f1" />;
      case '02d': return <CloudSun {...iconProps} color="#f59e0b" />;
      case '02n': return <CloudMoon {...iconProps} color="#6366f1" />;
      case '03d':
      case '03n':
      case '04d':
      case '04n': return <Cloud {...iconProps} color="#94a3b8" />;
      case '09d':
      case '09n': return <CloudDrizzle {...iconProps} color="#3b82f6" />;
      case '10d':
      case '10n': return <CloudRain {...iconProps} color="#2563eb" />;
      case '11d':
      case '11n': return <CloudLightning {...iconProps} color="#f59e0b" />;
      case '13d':
      case '13n': return <Snowflake {...iconProps} color="#add8e6" />;
      case '50d':
      case '50n': return <CloudFog {...iconProps} color="#94a3b8" />;
      default: return <Cloud {...iconProps} color="#94a3b8" />;
    }
  };

  return (
    <div className="forecast-row glass-panel animate-fade-in delay-100">
      <h3 className="section-title">5-Day Forecast</h3>
      <div className="forecast-items">
        {dailyForecasts.map((day, index) => (
          <div key={index} className="forecast-item">
            <span className="forecast-day">{getDayName(day.date)}</span>
            <div className="forecast-icon-wrapper">
              {getWeatherIcon(day.icon)}
            </div>
            <div className="forecast-temps">
              <span className="temp-max">{Math.round(day.maxTemp)}°</span>
              <span className="temp-min">{Math.round(day.minTemp)}°</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ForecastRow;
