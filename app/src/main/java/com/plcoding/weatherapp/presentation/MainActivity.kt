package com.plcoding.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.plcoding.weatherapp.presentation.ui.theme.Color1
import com.plcoding.weatherapp.presentation.ui.theme.Color2
import com.plcoding.weatherapp.presentation.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        setContent {
            WeatherAppTheme {
                val isLoading by viewModel.isLoading.collectAsState()
                val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    SwipeRefresh(
                        state = swipeRefreshState,
                        onRefresh = viewModel::loadData
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .background(Color1)
                        ) {
                            viewModel.state.error?.let { error ->
                                Spacer(modifier = Modifier.height(350.dp))
                                    Text(
                                        text = error,
                                        color = Color.Red,
                                        textAlign = TextAlign.Center,
                                    )
                            } ?: kotlin.run {
                                WeatherLocationDetail(state = viewModel.state)
                                DateInfo()
                                WeatherCard(
                                    state = viewModel.state,
                                    backgroundColor = Color2
                                )
                                HourlyWeatherForecast(state = viewModel.state)
                                DailyWeatherForecast(state = viewModel.state)
                                Spacer(modifier = Modifier.height(32.dp))
                            }
                        }
                    }
                }

            }
        }
    }
}