package com.tommunyiri.saftechweather.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tommunyiri.saftechweather.R
import com.tommunyiri.saftechweather.common.getCityName
import com.tommunyiri.saftechweather.presentation.components.LoadingIndicator


/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onSettingClicked: () -> Unit,
    onTryAgainClicked: () -> Unit
) {
    val homeViewModel = hiltViewModel<HomeScreenViewModel>()

    val state by viewModel.homeScreenState.collectAsStateWithLifecycle()

    var cityName by remember { mutableStateOf("") }

    homeViewModel.processIntent(HomeScreenIntent.GetCurrentTimeDate)

    if (state.isLoading) {
        LoadingIndicator()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LocalContext.current.getCityName(
            latitude = state.defaultLocation.latitude,
            longitude = state.defaultLocation.longitude
        ) { address ->
            cityName = address.locality.toString()
            homeViewModel.processIntent(HomeScreenIntent.LoadWeatherData)
        }
        HomeTopBar(
            onSettingClicked,
            onRefreshClicked = { homeViewModel.processIntent(HomeScreenIntent.RefreshWeatherData) })
        TopHeader(cityName, currentTimeDate = state.currentSystemTime, state)
    }
}

@Composable
fun HomeTopBar(onSettingClicked: () -> Unit, onRefreshClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        /*Text(
            text = cityName,
            style = MaterialTheme.typography.headlineLarge
        )*/
        Image(
            painter = painterResource(id = if (isSystemInDarkTheme()) R.drawable.ic_refresh_dark else R.drawable.ic_refresh),
            contentDescription = stringResource(R.string.home_content_description_refresh_icon),
            modifier = Modifier
                .defaultMinSize(40.dp)
                .clickable { onRefreshClicked() }
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Image(
            painter = painterResource(id = if (isSystemInDarkTheme()) R.drawable.ic_settings_dark else R.drawable.ic_settings),
            contentDescription = stringResource(R.string.home_content_description_setting_icon),
            modifier = Modifier
                .defaultMinSize(40.dp)
                .clickable { onSettingClicked() }
                .padding(8.dp)
        )
    }
}

@Composable
fun TopHeader(
    cityName: String, currentTimeDate: String, state: HomeScreenState
) {

    Box(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(16.dp),
        ) {
            Text(
                text = cityName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = currentTimeDate,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 8.dp, 0.dp, 0.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = if (state.prefTempUnit == stringResource(R.string.temp_unit_celsius)) "${state.weather?.temp_c} ${
                    stringResource(R.string.temp_symbol_celsius)
                } " else "${state.weather?.temp_f} ${stringResource(R.string.temp_symbol_fahrenheit)}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Normal,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp, 0.dp, 0.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = state.weather?.condition?.text.toString().uppercase(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp, 0.dp, 0.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}