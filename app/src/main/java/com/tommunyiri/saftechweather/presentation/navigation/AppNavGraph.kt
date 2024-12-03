package com.tommunyiri.saftechweather.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tommunyiri.saftechweather.presentation.screens.home.HomeScreen
import com.tommunyiri.saftechweather.presentation.screens.settings.SettingsScreen


/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */

@Composable
fun WeatherAppScreensNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Destinations.HOME.route) {
        composable(Destinations.HOME.route) {

            HomeScreen(
                onSettingClicked = {
                    navController.navigate(Destinations.SETTINGS.route) {
                        popUpTo(Destinations.HOME.route) {
                            inclusive = true
                        }
                    }
                },
                onTryAgainClicked = {
                    //homeViewModel.processIntent(HomeScreenIntent.LoadWeatherData)
                }
            )
        }
        composable(Destinations.SETTINGS.route) {
            SettingsScreen(onBackButtonClicked = {
                navController.navigate(Destinations.HOME.route) {
                    popUpTo(Destinations.SETTINGS.route) {
                        inclusive = true
                    }
                }
            })

            /*val settingsViewModel = hiltViewModel<SettingsViewModel>()
            val state = settingsViewModel
                .state
                .collectAsState(initial = SettingsScreenViewState())
                .value

            settingsViewModel.processIntent(SettingsScreenIntent.LoadSettingScreenData)

            SettingsScreen(
                state = state,
                onBackButtonClicked = { navController.navigate(Destinations.HOME.route) },
                onLanguageChanged = { selectedLanguage ->
                    settingsViewModel.processIntent(
                        SettingsScreenIntent.ChangeLanguage(
                            selectedLanguage
                        )
                    )
                },
                onUnitChanged = { selectedUnit ->
                    settingsViewModel.processIntent(SettingsScreenIntent.ChangeUnits(selectedUnit))
                },
                onTimeFormatChanged = { selectedFormat ->
                    settingsViewModel.processIntent(
                        SettingsScreenIntent.ChangeTimeFormat(selectedFormat)
                    )
                }
            )*/
        }
    }
}

enum class Destinations(val route: String) {
    HOME("home"),
    SETTINGS("settings")
}