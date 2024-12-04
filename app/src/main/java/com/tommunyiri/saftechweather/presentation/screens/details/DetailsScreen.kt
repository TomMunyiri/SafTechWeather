package com.tommunyiri.saftechweather.presentation.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tommunyiri.saftechweather.domain.model.Hour
import com.tommunyiri.saftechweather.presentation.components.LoadingIndicator
import com.tommunyiri.saftechweather.presentation.components.TopAppBarComponent

/**
 * Created by Tom Munyiri on 04/12/2024.
 * Company:
 * Email:
 */

@Composable
fun DetailsScreen(
    selectedDate: String,
    onBackButtonClicked: () -> Unit,
    viewModel: DetailsScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.detailsScreenState.collectAsStateWithLifecycle()

    var cityName by remember { mutableStateOf("") }

    LaunchedEffect(key1 = selectedDate) {
        if (selectedDate != null) {
            viewModel.processIntent(DetailsScreenIntent.LoadWeatherData(selectedDate))
        }
    }

    if (state.isLoading) {
        LoadingIndicator()
    }

    Column(modifier = Modifier.padding(1.dp)) {
        TopAppBarComponent(
            // title = stringResource(id = R.string.settings_screen_title),
            title = "Weather for $selectedDate",
            onBackButtonClick = onBackButtonClicked,
        )
        state.hourlyWeatherList?.let { hourlyWeatherList ->
            HourlyWeatherList(hourlyWeatherList)
        }
    }
}

@Composable
fun HourlyWeatherList(hourlyWeatherList: List<Hour>) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp),
    ) {
        items(hourlyWeatherList.size) { i ->
            val hourlyWeather = hourlyWeatherList[i]
            HourWeatherItem(hourlyWeather)
            HorizontalDivider(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .width(1.dp),
            )
        }
    }
}

@Composable
fun HourWeatherItem(hour: Hour) {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Row {
            Text(
                text = hour.time,
                style = typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = "${hour.temp_c}°C / ${hour.temp_f}°F",
                style = typography.bodyMedium,
            )
        }
        Text(
            fontWeight = FontWeight.Bold,
            text = "Condition: ${hour.condition.text}",
            style = typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
        )
        Row {
            Text(
                text = "Humidity: ${hour.humidity}%",
                style = typography.bodyMedium,
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = "Wind Speed: ${hour.wind_kph} km/h",
                style = typography.bodyMedium,
            )
        }
    }
}
