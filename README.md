# Aura Weather 🌦️ | AI-Enhanced Forecasts

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Groq](https://img.shields.io/badge/Groq-f3f4f6?style=for-the-badge&logo=groq&logoColor=black)

A premium, full-stack weather application that combines live meteorological data with advanced Artificial Intelligence. Built with a stunning Glassmorphism UI and powered by Spring AI + Groq for instant, intelligent weather insights.

## 🌟 Key Features

- **📍 Smart Autolocation:** Automatically detects your current city on startup via browser geolocation.
- **🔍 Intelligent Search:** Real-time city suggestions and autocomplete as you type, powered by OpenWeatherMap Geo API.
- **🤖 AI Meteorologist (Groq/LLaMA 3):** Ask complex questions like *"Should I wear a jacket in Chandigarh tonight?"* and get blazing-fast, context-aware advice.
- **💎 Glassmorphism UI:** A sleek, premium dashboard with modern gradients, fluid animations, and high-quality vectorized Lucide icons.
- **🌓 Dynamic Themes:** Intelligent dark/light mode switching that respects your system preferences.
- **🏠 One-Click Reset:** Instantly return to your home location with a dedicated shortcut button or by clicking the logo.

## 🏗️ Technical Stack

- **Frontend:** React 19 (Vite), native CSS Variables, Lucide Icons.
- **Backend:** Java 17+, Spring Boot 3.5, Spring AI (OpenAI/Groq compliant).
- **Inference:** Groq API (llama3-70b-8192) for high-performance AI completions.
- **Weather Data:** OpenWeatherMap API (Current Weather & Geo Direct APIs).
- **Database:** MySQL 8 (configured via Docker Compose).

## 🚀 Getting Started

### Prerequisites
- JDK 17+
- Node.js & npm (v18+)
- Docker (for MySQL instance)

### 1. Database Setup
Spin up the MySQL container using the provided docker-compose file:
```bash
docker-compose up -d
```

### 2. Backend Setup
Set your API keys as environment variables:
```bash
# Windows (PowerShell)
$env:OPENWEATHERMAP_API_KEY="your_key"
$env:GROQ_API_KEY="your_groq_key"

# Linux / Mac / Git Bash
export OPENWEATHERMAP_API_KEY="your_key"
export GROQ_API_KEY="your_groq_key"
```

Navigate to `weather-backend` and launch:
```bash
cd weather-backend
./mvnw spring-boot:run
```
Swagger UI available at: `http://localhost:8080/api/v1/swagger-ui.html`

### 3. Frontend Setup
Navigate to `weather-frontend` and launch:
```bash
cd weather-frontend
npm install
npm run dev
```
Open `http://localhost:5173` in your browser.

## 📸 Dashboard Preview
The application features a fully responsive, glassmorphism-inspired dashboard. Hover over the weather icons to see smooth 3D transformations!

## 💡 Notes on AI
This app uses **Spring AI's ChatClient** with a customized system prompt that injects real-time weather JSON as context, allowing the LLM to provide highly accurate, location-specific recommendations.
