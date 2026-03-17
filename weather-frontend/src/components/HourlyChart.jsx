import React from 'react';
import { AreaChart, Area, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import './HourlyChart.css';

const HourlyChart = ({ hourlyForecasts }) => {
  if (!hourlyForecasts || hourlyForecasts.length === 0) return null;

  // Format data for Recharts
  const chartData = hourlyForecasts.map(item => {
    const date = new Date(item.timestamp * 1000);
    return {
      time: date.toLocaleTimeString([], { hour: 'numeric', hour12: true }),
      temp: Math.round(item.temp)
    };
  });

  const minTemp = Math.min(...chartData.map(d => d.temp)) - 2;
  const maxTemp = Math.max(...chartData.map(d => d.temp)) + 2;

  const CustomTooltip = ({ active, payload, label }) => {
    if (active && payload && payload.length) {
      return (
        <div className="custom-tooltip">
          <p className="label">{`${label}`}</p>
          <p className="intro">{`${payload[0].value}°`}</p>
        </div>
      );
    }
    return null;
  };

  return (
    <div className="hourly-chart glass-panel animate-fade-in delay-200">
      <h3 className="section-title">24-Hour Forecast</h3>
      <div className="chart-container">
        <ResponsiveContainer width="100%" height={220}>
          <AreaChart
            data={chartData}
            margin={{
              top: 15, right: 10, left: -20, bottom: 0,
            }}
          >
            <defs>
              <linearGradient id="colorTemp" x1="0" y1="0" x2="0" y2="1">
                <stop offset="5%" stopColor="#3b82f6" stopOpacity={0.6}/>
                <stop offset="95%" stopColor="#3b82f6" stopOpacity={0}/>
              </linearGradient>
            </defs>
            <XAxis 
              dataKey="time" 
              axisLine={false} 
              tickLine={false} 
              tick={{ fill: 'var(--text-muted)', fontSize: 12 }} 
              dy={10}
            />
            <YAxis 
              domain={[minTemp, maxTemp]} 
              axisLine={false} 
              tickLine={false} 
              tick={{ fill: 'var(--text-muted)', fontSize: 12 }} 
              dx={-10}
            />
            <Tooltip content={<CustomTooltip />} cursor={{ stroke: 'rgba(255,255,255,0.2)', strokeWidth: 1 }} />
            <Area 
              type="monotone" 
              dataKey="temp" 
              stroke="#3b82f6" 
              strokeWidth={3}
              fillOpacity={1} 
              fill="url(#colorTemp)" 
              activeDot={{ r: 6, fill: '#3b82f6', stroke: '#fff', strokeWidth: 2 }}
            />
          </AreaChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
};

export default HourlyChart;
