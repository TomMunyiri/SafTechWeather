package com.tommunyiri.saftechweather.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.core.yearMonth
import com.tommunyiri.saftechweather.R
import com.tommunyiri.saftechweather.common.getCityName
import com.tommunyiri.saftechweather.presentation.components.LoadingIndicator
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.YearMonth

/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */

@SuppressLint("NewApi")
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onSettingClicked: () -> Unit,
    onDateSelected: (String) -> Unit,
    adjacentMonths: Long = 500,
) {
    val state by viewModel.homeScreenState.collectAsStateWithLifecycle()

    var cityName by remember { mutableStateOf("") }

    viewModel.processIntent(HomeScreenIntent.GetCurrentTimeDate)

    if (state.isLoading) {
        LoadingIndicator()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LocalContext.current.getCityName(
            latitude = state.defaultLocation.latitude,
            longitude = state.defaultLocation.longitude,
        ) { address ->
            cityName = address.locality.toString()
        }

        LaunchedEffect(key1 = cityName) {
            viewModel.processIntent(HomeScreenIntent.LoadWeatherData)
        }

        HomeTopBar(
            onSettingClicked,
            onRefreshClicked = { viewModel.processIntent(HomeScreenIntent.RefreshWeatherData) },
        )
        TopHeader(cityName, currentTimeDate = state.currentSystemTime, state)

        //
        val currentMonth = remember { YearMonth.now() }
        val startMonth = remember { currentMonth.minusMonths(adjacentMonths) }
        val endMonth = remember { currentMonth.plusMonths(adjacentMonths) }
        val selections = remember { mutableStateListOf<CalendarDay>() }
        val daysOfWeek = remember { daysOfWeek() }

        val state =
            rememberCalendarState(
                startMonth = startMonth,
                endMonth = endMonth,
                firstVisibleMonth = currentMonth,
                firstDayOfWeek = daysOfWeek.first(),
            )
        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)
        SimpleCalendarTitle(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            currentMonth = visibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            },
        )
        HorizontalCalendar(
            modifier = Modifier.testTag("Calendar"),
            state = state,
            dayContent = { day ->
                Day(day, isSelected = selections.contains(day)) { clicked ->
                    onDateSelected.invoke("${clicked.date.yearMonth}-${if (clicked.date.dayOfMonth.toString().length == 1) "0${clicked.date.dayOfMonth}" else clicked.date.dayOfMonth}")
                    if (selections.contains(clicked)) {
                        selections.remove(clicked)
                    } else {
                        selections.add(clicked)
                    }
                }
            },
            monthHeader = {
                MonthHeader(daysOfWeek = daysOfWeek)
            },
        )
        //
    }
}

@Composable
fun SimpleCalendarTitle(
    modifier: Modifier,
    currentMonth: YearMonth,
    isHorizontal: Boolean = true,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        modifier = modifier.height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CalendarNavigationIcon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "Previous",
            onClick = goToPrevious,
            isHorizontal = isHorizontal,
        )
        Text(
            modifier =
                Modifier
                    .weight(1f)
                    .testTag("MonthTitle"),
            text = currentMonth.displayText(),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
        )
        CalendarNavigationIcon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Next",
            onClick = goToNext,
            isHorizontal = isHorizontal,
        )
    }
}

@Composable
private fun CalendarNavigationIcon(
    imageVector: ImageVector,
    contentDescription: String,
    isHorizontal: Boolean = true,
    onClick: () -> Unit,
) = Box(
    modifier =
        Modifier
            .fillMaxHeight()
            .aspectRatio(1f)
            .clip(shape = CircleShape)
            .clickable(role = Role.Button, onClick = onClick),
) {
    val rotation by animateFloatAsState(
        targetValue = if (isHorizontal) 0f else 90f,
        label = "CalendarNavigationIconAnimation",
    )
    Icon(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(4.dp)
                .align(Alignment.Center)
                .rotate(rotation),
        imageVector = imageVector,
        contentDescription = contentDescription,
    )
}

@Composable
private fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .testTag("MonthHeader"),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                text = dayOfWeek.displayText(),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@SuppressLint("NewApi")
@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: (CalendarDay) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .aspectRatio(1f) // This is important for square-sizing!
                .testTag("MonthDay")
                .padding(6.dp)
                .clip(CircleShape)
                .background(color = if (isSelected) colorResource(R.color.purple_200) else Color.Transparent)
                // Disable clicks on inDates/outDates
                .clickable(
                    enabled = day.position == DayPosition.MonthDate,
                    showRipple = !isSelected,
                    onClick = { onClick(day) },
                ),
        contentAlignment = Alignment.Center,
    ) {
        val textColor =
            when (day.position) {
                // Color.Unspecified will use the default text color from the current theme
                DayPosition.MonthDate -> if (isSelected) Color.White else Color.Unspecified
                DayPosition.InDate, DayPosition.OutDate -> colorResource(R.color.purple_200)
            }
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 14.sp,
        )
    }
}

@Composable
fun HomeTopBar(
    onSettingClicked: () -> Unit,
    onRefreshClicked: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
    ) {
        IconButton(onClick = { onRefreshClicked() }, modifier = Modifier.padding(0.dp)) {
            Icon(
                imageVector = Icons.Rounded.Refresh,
                contentDescription = stringResource(R.string.home_content_description_refresh_icon)
            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
        IconButton(onClick = { onSettingClicked() }, modifier = Modifier.padding(0.dp)) {
            Icon(
                imageVector = Icons.Rounded.Settings,
                contentDescription = stringResource(R.string.home_content_description_setting_icon)
            )
        }
    }
}

@Composable
fun TopHeader(
    cityName: String,
    currentTimeDate: String,
    state: HomeScreenState,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(16.dp, 6.dp, 16.dp, 36.dp),
        ) {
            Text(
                text = cityName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier =
                    Modifier
                        .fillMaxWidth(),
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
                text =
                    if (state.prefTempUnit == stringResource(R.string.temp_unit_celsius)) {
                        "${state.weather?.temp_c} ${
                            stringResource(R.string.temp_symbol_celsius)
                        } "
                    } else {
                        "${state.weather?.temp_f} ${stringResource(R.string.temp_symbol_fahrenheit)}"
                    },
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
