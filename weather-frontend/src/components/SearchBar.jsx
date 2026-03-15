import { useState, useEffect, useRef } from 'react';
import { Search, MapPin, Loader2 } from 'lucide-react';
import { fetchCitySuggestions } from '../services/api';
import './SearchBar.css';

const SearchBar = ({ onSearch }) => {
  const [query, setQuery] = useState('');
  const [suggestions, setSuggestions] = useState([]);
  const [loadingSuggestions, setLoadingSuggestions] = useState(false);
  const [loadingLocation, setLoadingLocation] = useState(false);
  const [showDropdown, setShowDropdown] = useState(false);
  
  const dropdownRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setShowDropdown(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  useEffect(() => {
    const getSuggestions = async () => {
      if (query.trim().length < 3) {
        setSuggestions([]);
        return;
      }
      
      setLoadingSuggestions(true);
      try {
        const data = await fetchCitySuggestions(query);
        setSuggestions(data);
        setShowDropdown(true);
      } catch (err) {
        console.error(err);
      } finally {
        setLoadingSuggestions(false);
      }
    };

    const debounceTimer = setTimeout(getSuggestions, 400);
    return () => clearTimeout(debounceTimer);
  }, [query]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (query.trim()) {
      onSearch(query.trim());
      setShowDropdown(false);
      setQuery('');
    }
  };

  const handleSuggestionClick = (suggestion) => {
    const fullName = `${suggestion.name}${suggestion.state ? `, ${suggestion.state}` : ''}, ${suggestion.country}`;
    onSearch(fullName);
    setQuery('');
    setShowDropdown(false);
  };

  const handleLocation = () => {
    if (navigator.geolocation) {
      setLoadingLocation(true);
      navigator.geolocation.getCurrentPosition(
        async (position) => {
          try {
            const { latitude, longitude } = position.coords;
            const response = await fetch(`https://nominatim.openstreetmap.org/reverse?format=json&lat=${latitude}&lon=${longitude}&zoom=10`);
            const data = await response.json();
            
            const city = data.address.city || data.address.town || data.address.village || data.address.county;
            if (city) {
              onSearch(city);
            } else {
              alert("Sorry, couldn't determine your city from the location.");
            }
          } catch (error) {
            console.error("Error reverse geocoding:", error);
            alert("Failed to get city from coordinates.");
          } finally {
            setLoadingLocation(false);
          }
        },
        (error) => {
          console.error("Error getting location", error);
          alert("Please enable location services to use this feature.");
          setLoadingLocation(false);
        }
      );
    } else {
      alert("Geolocation is not supported by your browser.");
    }
  };

  return (
    <div className="search-container" ref={dropdownRef}>
      <form className="search-bar glass-panel animate-fade-in" onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Search for a city..."
          value={query}
          onChange={(e) => {
            setQuery(e.target.value);
            setShowDropdown(true);
          }}
          className="search-input"
          disabled={loadingLocation}
          autoComplete="off"
        />
        <button 
          type="button" 
          className="icon-btn" 
          onClick={handleLocation} 
          title="Use my location"
          disabled={loadingLocation}
        >
          {loadingLocation ? <Loader2 size={20} className="spin" /> : <MapPin size={20} />}
        </button>
        <button type="submit" className="icon-btn search-btn" title="Search" disabled={loadingLocation}>
          <Search size={20} />
        </button>
      </form>

      {showDropdown && query.length >= 3 && (
        <ul className="suggestions-dropdown glass-panel">
          {loadingSuggestions ? (
            <li className="suggestion-item loading-item">
              <Loader2 size={16} className="spin" /> Loading...
            </li>
          ) : suggestions.length > 0 ? (
            suggestions.map((s, index) => (
              <li 
                key={`${s.lat}-${s.lon}-${index}`} 
                className="suggestion-item"
                onClick={() => handleSuggestionClick(s)}
              >
                <MapPin size={16} className="suggestion-icon" />
                <span className="suggestion-name">{s.name}</span>
                <span className="suggestion-region">
                  {s.state ? `${s.state}, ` : ''}{s.country}
                </span>
              </li>
            ))
          ) : (
             <li className="suggestion-item no-results">No cities found</li>
          )}
        </ul>
      )}
    </div>
  );
};

export default SearchBar;
