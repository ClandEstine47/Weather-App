package com.plcoding.weatherapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun DailyWeatherDisplay(
    weatherType: Int?,
    isFirst: Boolean,
    dayWeek: String?,
    month: String?,
    description: String?,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = if (isFirst) "Today" else dayWeek!!,
            color = Color.LightGray
        )
        Text(
            text = month!!,
            color = Color.LightGray
        )
        Image(
            painter = painterResource(id = weatherType!!),
            contentDescription = "image",
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = description!!,
            color = textColor,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}