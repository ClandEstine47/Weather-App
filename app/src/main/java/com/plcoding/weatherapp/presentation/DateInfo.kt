package com.plcoding.weatherapp.presentation

import android.widget.TextClock
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale

@Composable
fun DateInfo(
    modifier: Modifier = Modifier
) {
    val currentDate = LocalDate.now()
    val dayOfWeek = currentDate.dayOfWeek
    val currentTime = LocalTime.now()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        var updatedDayOfWeek = dayOfWeek.toString()
        val firstChar = updatedDayOfWeek[0]
        val restOfString = updatedDayOfWeek.substring(1).lowercase(Locale.ROOT)
        updatedDayOfWeek = firstChar + restOfString
        Text(
            text = updatedDayOfWeek,
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = "|", color = Color.White, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(5.dp))
        AndroidView(
            factory = { context ->
                TextClock(context).apply {
                    format12Hour?.let { this.format12Hour = "h:mm a" }
                    timeZone?.let { this.timeZone = it }
                    textSize.let { this.textSize = 20f }
                    setTextColor(Color.White.toArgb())
                }
            }
        )
    }
}
