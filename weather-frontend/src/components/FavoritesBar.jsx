import React from 'react';
import { Star, X } from 'lucide-react';
import './FavoritesBar.css';

const FavoritesBar = ({ favorites, onSelectFavorite, onRemoveFavorite }) => {
  if (!favorites || favorites.length === 0) return null;

  return (
    <div className="favorites-bar animate-fade-in delay-100">
      <div className="favorites-label">
        <Star size={14} className="favorites-icon" />
        <span>Saved Locations:</span>
      </div>
      <div className="favorites-list">
        {favorites.map((city, index) => (
          <div key={`${city}-${index}`} className="favorite-chip">
            <button 
              className="favorite-select-btn"
              onClick={() => onSelectFavorite(city)}
            >
              {city}
            </button>
            <button 
              className="favorite-remove-btn"
              onClick={(e) => {
                e.stopPropagation();
                onRemoveFavorite(city);
              }}
              title={`Remove ${city}`}
            >
              <X size={12} />
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FavoritesBar;
