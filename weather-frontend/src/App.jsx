import { useState, useEffect } from 'react';
import { Sun, Moon, CloudSun, Home } from 'lucide-react';
import SearchBar from './components/SearchBar';
import WeatherCard from './components/WeatherCard';
import AIAssistant from './components/AIAssistant';
import { fetchCurrentWeather } from './services/api';
import './App.css';

function App() {
  const [theme, setTheme] = useState('light');
  const [weatherData, setWeatherData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [currentCity, setCurrentCity] = useState('');
  const [homeCity, setHomeCity] = useState(null);

  // Check system preference on load
  useEffect(() => {
    if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
      setTheme('dark');
    }
  }, []);

  // Apply theme class to document body
  useEffect(() => {
    document.documentElement.setAttribute('data-theme', theme);
  }, [theme]);

  const toggleTheme = () => {
    setTheme(prev => (prev === 'light' ? 'dark' : 'light'));
  };

  const handleSearch = async (city) => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchCurrentWeather(city);
      setWeatherData(data);
      setCurrentCity(data.city); // Prefer the API's resolved city name
    } catch (err) {
      setError('City not found or unable to fetch weather data. Please try again.');
      setWeatherData(null);
      setCurrentCity('');
    } finally {
      setLoading(false);
    }
  };

  const returnToHome = () => {
    if (homeCity) {
      handleSearch(homeCity);
    } else {
      // If for some reason homeCity wasn't set, try geolocation again
      window.location.reload(); 
    }
  };

  // Auto-load by geolocation or default city on first load
  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        async (position) => {
          try {
            const { latitude, longitude } = position.coords;
            const response = await fetch(`https://nominatim.openstreetmap.org/reverse?format=json&lat=${latitude}&lon=${longitude}&zoom=10`);
            const data = await response.json();
            const city = data.address.city || data.address.town || data.address.village || data.address.county;
            if (city) {
              setHomeCity(city);
              handleSearch(city);
            } else {
              setHomeCity('London');
              handleSearch('London');
            }
          } catch (err) {
            console.error("Autolocation error:", err);
            setHomeCity('London');
            handleSearch('London');
          }
        },
        (error) => {
          console.warn("Geolocation denied or error:", error);
          setHomeCity('London');
          handleSearch('London');
        }
      );
    } else {
      setHomeCity('London');
      handleSearch('London');
    }
  }, []);

  return (
    <div className="app-container">
      <header className="header glass-panel animate-fade-in">
        <div className="logo" onClick={returnToHome} style={{ cursor: 'pointer' }} title="Return to current location">
          <CloudSun size={32} />
          <span>Aura Weather</span>
        </div>
        
        <div className="header-actions">
          <SearchBar onSearch={handleSearch} />
          <button className="home-btn" onClick={returnToHome} title="My Location">
            <Home size={20} />
          </button>
        </div>

        <button className="theme-toggle" onClick={toggleTheme} aria-label="Toggle theme">
          {theme === 'light' ? <Moon size={20} /> : <Sun size={20} />}
        </button>
      </header>

      {loading ? (
        <div className="loader-container">
          <div className="spinner"></div>
          <p>Gathering weather data...</p>
        </div>
      ) : error ? (
        <div className="error-message animate-fade-in">{error}</div>
      ) : weatherData ? (
        <main className="main-content">
          <WeatherCard data={weatherData} />
          <AIAssistant city={currentCity} />
        </main>
      ) : (
        <div className="loader-container animate-fade-in">
          <p>Search for a city to get started</p>
        </div>
      )}
    </div>
  );
}

export default App;
