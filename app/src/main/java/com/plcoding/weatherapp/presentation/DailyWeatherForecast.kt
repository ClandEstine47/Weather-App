package com.plcoding.weatherapp.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.weatherapp.domain.weather.WeatherData
import com.plcoding.weatherapp.domain.weather.WeatherType
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DailyWeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    val today = remember {
        DayOfWeek.from(LocalDateTime.now())
    }
    var weeklyWeatherType = remember(state) {
        state.weatherInfo?.weatherTypePerDay?.toList()
    }
    val indexList = listOf(0, 1, 2, 3, 4, 5, 6)

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
            LazyRow() {
                weeklyWeatherType.forEachIndexed { index, weatherType ->
                    item(contentType = weatherType) {
                        DailyWeatherDisplay(
                            weatherType = weatherType,
                            day = today.plus(index.toLong()).getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                            modifier = Modifier
                                .height(120.dp)
                                .width(90.dp)
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    } else {
        Log.d("ERROR", "WeatherForecast: NULL VALUE")
    }
}