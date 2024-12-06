package com.tommunyiri.saftechweather.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tommunyiri.saftechweather.presentation.screens.about.AboutScreen
import com.tommunyiri.saftechweather.presentation.screens.details.DetailsScreen
import com.tommunyiri.saftechweather.presentation.screens.home.HomeScreen
import com.tommunyiri.saftechweather.presentation.screens.settings.SettingsScreen

/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */

@Composable
fun WeatherAppScreensNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destinations.HOME.route) {
        composable(Destinations.HOME.route) {
            HomeScreen(
                onSettingClicked = {
                    navController.navigate(Destinations.SETTINGS.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onDateSelected = { selectedDate ->
                    // Navigate to DetailsScreen with the selected date as an argument
                    navController.navigate("details/$selectedDate") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
        composable(Destinations.SETTINGS.route) {
            SettingsScreen(
                onBackButtonClicked = {
                    navController.navigate(Destinations.HOME.route) {
                        popUpTo(Destinations.SETTINGS.route) {
                            inclusive = true
                        }
                    }
                    navController.popBackStack()
                },
                onAboutClicked = {
                    navController.navigate(Destinations.ABOUT.route)
                })
        }
        composable(
            route = "details/{selectedDate}",
            arguments = listOf(navArgument("selectedDate") { type = NavType.StringType }),
        ) { backStackEntry ->
            val selectedDate = backStackEntry.arguments?.getString("selectedDate") ?: ""
            DetailsScreen(
                selectedDate = selectedDate,
                onBackButtonClicked = {
                    navController.navigate(Destinations.HOME.route) {
                        popUpTo(Destinations.DETAILS.route) {
                            inclusive = true
                        }
                    }
                    navController.popBackStack()
                },
            )
        }
        composable(Destinations.ABOUT.route) {
            AboutScreen(
                onBackButtonClicked = {
                    navController.navigate(Destinations.SETTINGS.route) {
                        popUpTo(Destinations.ABOUT.route) {
                            inclusive = true
                        }
                    }
                    navController.popBackStack()
                })
        }
    }
}

enum class Destinations(val route: String) {
    HOME("home"),
    SETTINGS("settings"),
    ABOUT("about"),
    DETAILS("details/{selectedDate}"),
}
