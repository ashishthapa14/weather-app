import React, { useState, useEffect } from 'react';
import { AlertTriangle, X } from 'lucide-react';
import './AlertBanner.css';

const AlertBanner = ({ alerts }) => {
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    if (alerts && alerts.length > 0) {
      setVisible(true);
    } else {
      setVisible(false);
    }
  }, [alerts]);

  if (!visible || !alerts || alerts.length === 0) return null;

  return (
    <div className="alert-banner-container animate-slide-down">
      {alerts.map((alert, index) => (
        <div key={index} className="alert-banner">
          <div className="alert-content">
            <AlertTriangle className="alert-icon" size={20} />
            <div className="alert-text">
              <span className="alert-headline">{alert.event || 'Weather Alert'}</span>
              <span className="alert-description">{alert.description}</span>
            </div>
          </div>
          <button className="alert-close" onClick={() => setVisible(false)}>
            <X size={20} />
          </button>
        </div>
      ))}
    </div>
  );
};

export default AlertBanner;
