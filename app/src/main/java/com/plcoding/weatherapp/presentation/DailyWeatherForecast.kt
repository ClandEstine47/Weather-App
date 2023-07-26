package com.plcoding.weatherapp.presentation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DailyWeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    val todayWeek = remember {
        DayOfWeek.from(LocalDateTime.now())
    }
    val month = remember {
        LocalDateTime.now()
    }
    var weeklyWeatherTypeImage = remember(state) {
        state.weatherInfo?.weatherTypePerDayImage?.toList()
    }
    var weeklyWeatherTypeDescription = remember(state) {
        state.weatherInfo?.weatherTypePerDayDescription?.toList()
    }

    val indexList = listOf(0, 1, 2, 3, 4, 5, 6)

    if (!weeklyWeatherTypeImage.isNullOrEmpty() && !weeklyWeatherTypeDescription.isNullOrEmpty()) {
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
                weeklyWeatherTypeImage.forEachIndexed { index, weatherType ->
                    item(contentType = weatherType) {
                        DailyWeatherDisplay(
                            weatherType = weatherType,
                            isFirst = index == 0,
                            dayWeek = todayWeek.plus(index.toLong()).getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                            month = month.plusDays(index.toLong()).format(DateTimeFormatter.ofPattern("MMM d")),
                            description = weeklyWeatherTypeDescription[index],
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