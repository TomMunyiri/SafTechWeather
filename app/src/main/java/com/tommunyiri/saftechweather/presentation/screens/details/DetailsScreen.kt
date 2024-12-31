package com.tommunyiri.saftechweather.presentation.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tommunyiri.saftechweather.domain.model.Hour
import com.tommunyiri.saftechweather.presentation.components.LoadingIndicator
import com.tommunyiri.saftechweather.presentation.components.TopAppBarComponent
import com.tommunyiri.saftechweather.presentation.components.WeatherDataStatusText
import com.tommunyiri.saftechweather.presentation.ui.theme.WeatherUpdatedGreenBackground
import com.tommunyiri.saftechweather.presentation.ui.theme.WeatherUpdatedGreenBorder
import com.tommunyiri.saftechweather.presentation.ui.theme.WeatherUpdatedGreenText
import com.tommunyiri.saftechweather.presentation.ui.theme.WeatherUpdatedRedBackground
import com.tommunyiri.saftechweather.presentation.ui.theme.WeatherUpdatedRedBorder
import com.tommunyiri.saftechweather.presentation.ui.theme.WeatherUpdatedRedText

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

    LaunchedEffect(key1 = selectedDate) {
        viewModel.processIntent(DetailsScreenIntent.LoadWeatherData(selectedDate))
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBarComponent(
            title = "Weather for $selectedDate",
            onBackButtonClick = onBackButtonClicked,
            actions = {
                IconButton(onClick = {
                    viewModel.processIntent(DetailsScreenIntent.RefreshWeatherData(selectedDate))
                }) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Localized description"
                    )
                }
            })
        if (state.isLoading) {
            LoadingIndicator()
        }
        state.hourlyWeatherList?.let { hourlyWeatherList ->
            Column {
                if (state.isWeatherUpToDate == true) {
                    WeatherDataStatusText(
                        "Updated: ${state.modifiedAt}",
                        WeatherUpdatedGreenText,
                        WeatherUpdatedGreenBorder,
                        WeatherUpdatedGreenBackground
                    )
                } else {
                    WeatherDataStatusText(
                        "Updated: ${state.modifiedAt}",
                        WeatherUpdatedRedText,
                        WeatherUpdatedRedBorder,
                        WeatherUpdatedRedBackground
                    )
                }
                HourlyWeatherList(hourlyWeatherList)
            }
        }
        state.error?.let { error ->
            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                text = error,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}

@Composable
fun HourlyWeatherList(hourlyWeatherList: List<Hour>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp),
    ) {
        items(hourlyWeatherList.size) { i ->
            val hourlyWeather = hourlyWeatherList[i]
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(1.dp),
            )
            HourWeatherItem(hourlyWeather)
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
