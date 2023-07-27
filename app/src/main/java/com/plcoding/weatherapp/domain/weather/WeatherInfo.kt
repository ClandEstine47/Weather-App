package com.plcoding.weatherapp.domain.weather

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>,
    val currentWeatherData: WeatherData?,
    val weatherTypePerDayImage: MutableList<Int?>,
    val weatherTypePerDayDescription: MutableList<String?>,
    val weatherTemperature: MutableList<Double>
)
