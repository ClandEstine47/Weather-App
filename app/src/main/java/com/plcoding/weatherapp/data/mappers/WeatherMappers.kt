package com.plcoding.weatherapp.data.mappers

import com.plcoding.weatherapp.data.remote.WeatherDataDto
import com.plcoding.weatherapp.data.remote.WeatherDto
import com.plcoding.weatherapp.domain.weather.WeatherData
import com.plcoding.weatherapp.domain.weather.WeatherInfo
import com.plcoding.weatherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCode[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = if (now.minute < 30) {
        weatherDataMap[0]?.find {
            it.time.hour == now.hour
        }
    } else if (now.hour + 1 == 0) {
        weatherDataMap[1]?.find {
            it.time.hour == now.hour + 1
        }
    } else {
        weatherDataMap[0]?.find {
            it.time.hour == now.hour + 1
        }
    }

    var weeklyWeatherTypeImage = mutableListOf<Int?>()
    var weeklyWeatherTypeDescription = mutableListOf<String?>()
    var weeklyWeatherTypeTemperature = mutableListOf<List<Double>>()

    weatherDataMap.forEach { (_, dailyWeatherData) ->
        var dailyTypesImage = mutableListOf<Int>()
        var dailyTypesDescription = mutableListOf<String>()
        var dailyTypesTemperature = mutableListOf<Double>()
        dailyWeatherData.forEach { weatherData ->
            dailyTypesImage.add(weatherData.weatherType.iconRes)
            dailyTypesDescription.add(weatherData.weatherType.weatherDesc)
            dailyTypesTemperature.add(weatherData.temperatureCelsius)
        }
        weeklyWeatherTypeImage.add(calculateHighestOccurrenceImage(dailyTypesImage))
        weeklyWeatherTypeDescription.add(calculateHighestOccurrenceDescription(dailyTypesDescription))
        val sortedTemperatureList = dailyTypesTemperature.sorted()
        val tempPerDay = listOf<Double>(sortedTemperatureList.first(), sortedTemperatureList.last())
        weeklyWeatherTypeTemperature.add(tempPerDay)
    }

    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData,
        weatherTypePerDayImage = weeklyWeatherTypeImage,
        weatherTypePerDayDescription = weeklyWeatherTypeDescription,
        weatherTemperature = weeklyWeatherTypeTemperature
    )
}

fun calculateHighestOccurrenceImage(dailyTypes: MutableList<Int>): Int? {
    var map = HashMap<Int, Int>()
    dailyTypes.forEach {
        if (map.containsKey(it)) {
            map[it] = map.getValue(it) + 1
        } else {
            map[it] = 1
        }
    }
    val entryWithLargestValue = map.entries.maxByOrNull { it.value }
    return entryWithLargestValue?.key
}

fun calculateHighestOccurrenceDescription(dailyTypes: MutableList<String>): String? {
    var map = HashMap<String, Int>()
    dailyTypes.forEach {
        if (map.containsKey(it)) {
            map[it] = map.getValue(it) + 1
        } else {
            map[it] = 1
        }
    }
    val entryWithLargestValue = map.entries.maxByOrNull { it.value }
    return entryWithLargestValue?.key
}