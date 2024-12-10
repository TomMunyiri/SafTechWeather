package com.tommunyiri.saftechweather.presentation.screens.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tommunyiri.saftechweather.R
import com.tommunyiri.saftechweather.presentation.components.SettingsItem
import com.tommunyiri.saftechweather.presentation.components.SingleSelectDialog
import com.tommunyiri.saftechweather.presentation.components.TopAppBarComponent

/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */

@Composable
fun SettingsScreen(
    onBackButtonClicked: () -> Unit, onAboutClicked: () -> Unit,
    viewModel: SettingsScreenViewModel = hiltViewModel(), onThemeUpdated: () -> Unit
) {
    val state by viewModel.settingsScreenState.collectAsStateWithLifecycle()

    viewModel.processIntent(SettingsScreenIntent.GetDefaultTempUnit)
    viewModel.processIntent(SettingsScreenIntent.GetTheme)

    val showTempDialog = remember { mutableStateOf(false) }
    val showThemeDialog = remember { mutableStateOf(false) }
    if (showTempDialog.value) {
        val temperatureUnits =
            LocalContext.current.resources.getStringArray(R.array.unit_values_array).toList()
        val selectedTempUnit =
            if (state.prefTempUnit == LocalContext.current.getString(R.string.temp_unit_celsius)) 0 else 1
        SingleSelectDialog(
            optionsList = temperatureUnits,
            defaultSelected = selectedTempUnit,
            onCancelButtonClick = { showTempDialog.value = false },
            onDismissRequest = { showTempDialog.value = false },
            onItemSelected = {
                viewModel.processIntent(SettingsScreenIntent.SaveDefaultTempUnit(it))
            },
        )
    }

    if (showThemeDialog.value) {
        val themes =
            LocalContext.current.resources.getStringArray(R.array.theme_values_array).toList()
        val selectedTheme = when (state.prefTheme) {
            LocalContext.current.getString(R.string.follow_system_value) -> 0
            LocalContext.current.getString(R.string.light_theme_value) -> 1
            else -> 2
        }
        SingleSelectDialog(
            optionsList = themes,
            defaultSelected = selectedTheme,
            onCancelButtonClick = { showThemeDialog.value = false },
            onDismissRequest = { showThemeDialog.value = false },
            onItemSelected = {
                viewModel.processIntent(SettingsScreenIntent.SaveTheme(it))
                onThemeUpdated.invoke()
            },
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBarComponent(
            title = stringResource(id = R.string.settings_screen_title),
            onBackButtonClick = onBackButtonClicked,
        )
        state.prefTempUnit?.let { defaultTempUnit ->
            SettingsItem(
                itemLabel = stringResource(R.string.settings_temp_unit),
                itemIcon = R.drawable.ic_temp_unit,
                itemIconContentDescription = stringResource(R.string.settings_content_description_temp_unit_icon),
                onItemClicked = { showTempDialog.value = true },
                itemValue = defaultTempUnit
            )
        }
        state.prefTheme?.let { prefTheme ->
            SettingsItem(
                itemLabel = stringResource(R.string.settings_theme),
                itemIcon = R.drawable.ic_palette,
                itemIconContentDescription = stringResource(R.string.settings_content_description_theme_icon),
                onItemClicked = { showThemeDialog.value = true },
                itemValue = prefTheme
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(1.dp)
            )
        }

        SettingsItem(
            itemLabel = stringResource(R.string.settings_about),
            itemIcon = R.drawable.ic_info,
            itemIconContentDescription = stringResource(R.string.settings_content_description_about_icon),
            onItemClicked = { onAboutClicked() }
        )
    }
}
