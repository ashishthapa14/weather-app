import { 
  Cloud, 
  Droplets, 
  Wind, 
  Sunrise, 
  Sunset, 
  MapPin, 
  Sun, 
  CloudRain, 
  CloudLightning, 
  CloudDrizzle, 
  Snowflake, 
  CloudFog, 
  Moon,
  CloudSun,
  CloudMoon,
  ThermometerSun,
  Star
} from 'lucide-react';
import './WeatherCard.css';

const WeatherCard = ({ data, isFavorite, onToggleFavorite }) => {
  if (!data) return null;

    const {
    city,
    country,
    temperature,
    feelsLike,
    humidity,
    windSpeed,
    condition,
    description,
    icon,
    sunrise,
    sunset,
    aqi,
    uvi,
  } = data;

  const formatTime = (timestamp) => {
    return new Date(timestamp * 1000).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  };

  const getAqiInfo = (aqiValue) => {
    if (!aqiValue) return { text: 'Unknown', color: '#94a3b8', className: 'aqi-unknown' };
    switch (aqiValue) {
      case 1: return { text: 'Good', color: '#22c55e', className: 'aqi-good' };
      case 2: return { text: 'Fair', color: '#eab308', className: 'aqi-fair' };
      case 3: return { text: 'Moderate', color: '#f97316', className: 'aqi-moderate' };
      case 4: return { text: 'Poor', color: '#ef4444', className: 'aqi-poor' };
      case 5: return { text: 'Very Poor', color: '#7f1d1d', className: 'aqi-very-poor' };
      default: return { text: 'Unknown', color: '#94a3b8', className: 'aqi-unknown' };
    }
  };

  const aqiInfo = getAqiInfo(aqi);

  const getUviInfo = (uviValue) => {
    if (uviValue === undefined || uviValue === null) return { text: 'Unknown', color: '#94a3b8' };
    if (uviValue <= 2.9) return { text: 'Low', color: '#22c55e' }; // Green
    if (uviValue <= 5.9) return { text: 'Moderate', color: '#eab308' }; // Yellow
    if (uviValue <= 7.9) return { text: 'High', color: '#f97316' }; // Orange
    if (uviValue <= 10.9) return { text: 'Very High', color: '#ef4444' }; // Red
    return { text: 'Extreme', color: '#a855f7' }; // Purple
  };

  const uviInfo = getUviInfo(uvi);

  const getWeatherIcon = (iconCode) => {
    const iconProps = { className: "weather-icon-svg", size: 100 };
    
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
    <div className="weather-card glass-panel animate-fade-in">
      <div className="weather-main">
        <div className="weather-location">
          <MapPin className="location-icon" size={24} />
          <h2>{city}, {country}</h2>
          <button 
            className={`favorite-btn ${isFavorite ? 'active' : ''}`}
            onClick={() => onToggleFavorite(city)}
            title={isFavorite ? "Remove from favorites" : "Add to favorites"}
          >
            <Star size={24} fill={isFavorite ? "#eab308" : "none"} color={isFavorite ? "#eab308" : "#94a3b8"} />
          </button>
        </div>
        <p className="weather-desc">{description}</p>
        
        <div className="temp-container">
          <div className="weather-icon-wrapper">
            {getWeatherIcon(icon)}
          </div>
          <div className="temp-display">
            <h1>{Math.round(temperature)}°</h1>
            <p>Feels like {Math.round(feelsLike)}°</p>
          </div>
        </div>
      </div>

      <div className="weather-details">
        <div className="detail-item">
          <Droplets size={24} className="detail-icon humidity" />
          <div className="detail-info">
            <span className="detail-label">Humidity</span>
            <span className="detail-value">{humidity}%</span>
          </div>
        </div>
        
        <div className="detail-item">
          <Wind size={24} className="detail-icon wind" />
          <div className="detail-info">
            <span className="detail-label">Wind</span>
            <span className="detail-value">{windSpeed} m/s</span>
          </div>
        </div>

        <div className="detail-item">
          <Sunrise size={24} className="detail-icon sun" />
          <div className="detail-info">
            <span className="detail-label">Sunrise</span>
            <span className="detail-value">{formatTime(sunrise)}</span>
          </div>
        </div>

        <div className="detail-item">
          <Sunset size={24} className="detail-icon sun" />
          <div className="detail-info">
            <span className="detail-label">Sunset</span>
            <span className="detail-value">{formatTime(sunset)}</span>
          </div>
        </div>

        {aqi && (
          <div className={`detail-item aqi-container ${aqiInfo.className}`}>
            <Wind size={24} className="detail-icon aqi" color={aqiInfo.color} />
            <div className="detail-info">
              <span className="detail-label">Air Quality</span>
              <span className="detail-value" style={{ color: aqiInfo.color }}>
                {aqiInfo.text}
              </span>
            </div>
          </div>
        )}

        {uvi !== undefined && (
          <div className="detail-item uvi-container">
            <ThermometerSun size={24} className="detail-icon uvi" color={uviInfo.color} />
            <div className="detail-info">
              <span className="detail-label">UV Index</span>
              <span className="detail-value" style={{ color: uviInfo.color }}>
                {uviInfo.text} ({Math.round(uvi)})
              </span>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default WeatherCard;
