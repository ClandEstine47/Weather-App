package com.plcoding.weatherapp.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.weatherapp.domain.weather.WeatherData
import com.plcoding.weatherapp.domain.weather.WeatherType
import java.time.LocalDateTime

@Composable
fun DailyWeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    var weeklyWeatherType = remember(state) {
        state.weatherInfo?.weatherTypePerDay?.toList()
    }

//    var now = if (LocalDateTime.now().minute > 30) LocalDateTime.now().hour + 1 else LocalDateTime.now().hour

//    var todayHourlyForecastData = remember(state) {
//        state.weatherInfo?.weatherDataPerDay?.get(0)?.filter {
//            it.time.hour >= now
//        }
//    }
//    var tomorrowHourlyForecastData = remember(state) {
//        state.weatherInfo?.weatherDataPerDay?.get(1)?.take(24 - todayHourlyForecastData!!.size)
//    }
//    var hourlyForecastData: List<WeatherData>? = todayHourlyForecastData?.plus(
//        tomorrowHourlyForecastData!!
//    )
    if (!weeklyWeatherType.isNullOrEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "DAILY FORECAST",
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(content = {
                items(weeklyWeatherType!!) {
                    Image(
                        painter = painterResource(id = it!!),
                        contentDescription = "img",
                        modifier = Modifier.width(200.dp)
                    )
                }
            })
        }
    } else {
        Log.d("ERROR", "WeatherForecast: NULL VALUE")
    }
}