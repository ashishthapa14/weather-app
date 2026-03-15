import { useState } from 'react';
import { Bot, Send, Loader2 } from 'lucide-react';
import { fetchAIInsights } from '../services/api';
import './AIAssistant.css';

const AIAssistant = ({ city }) => {
  const [query, setQuery] = useState('');
  const [insight, setInsight] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!query.trim() || !city) return;

    setLoading(true);
    setError(null);
    try {
      const data = await fetchAIInsights(city, query);
      setInsight(data.aiInsight);
    } catch (err) {
      setError('Unable to get AI insights right now. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  if (!city) {
    return (
      <div className="ai-assistant glass-panel animate-fade-in empty-state">
        <Bot size={40} className="ai-icon-large" />
        <p>Search for a city to chat with our AI Meteorologist.</p>
      </div>
    );
  }

  return (
    <div className="ai-assistant glass-panel animate-fade-in">
      <div className="ai-header">
        <Bot size={28} className="ai-icon" />
        <h3>AI Meteorologist</h3>
      </div>

      <div className="ai-content">
        {insight ? (
          <div className="ai-response highlight">
            <p>{insight}</p>
          </div>
        ) : (
          <div className="ai-response">
            <p>Ask me anything about the weather in {city}! (e.g., "Do I need an umbrella tomorrow?")</p>
          </div>
        )}
        
        {error && <p className="ai-error">{error}</p>}
      </div>

      <form className="ai-input-form" onSubmit={handleSubmit}>
        <input
          type="text"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Ask a question..."
          className="ai-input"
          disabled={loading}
        />
        <button type="submit" className="ai-submit-btn" disabled={loading || !query.trim()}>
          {loading ? <Loader2 size={18} className="spin" /> : <Send size={18} />}
        </button>
      </form>
    </div>
  );
};

export default AIAssistant;
