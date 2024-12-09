package com.tommunyiri.saftechweather.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.tommunyiri.saftechweather.common.CheckForPermissions
import com.tommunyiri.saftechweather.common.OnPermissionDenied
import com.tommunyiri.saftechweather.common.createLocationRequest
import com.tommunyiri.saftechweather.core.MainViewIntent
import com.tommunyiri.saftechweather.core.MainViewModel
import com.tommunyiri.saftechweather.core.MainViewState
import com.tommunyiri.saftechweather.presentation.components.EnableLocationSettingScreen
import com.tommunyiri.saftechweather.presentation.components.LoadingIndicator
import com.tommunyiri.saftechweather.presentation.components.RequiresPermissionsScreen
import com.tommunyiri.saftechweather.presentation.navigation.WeatherAppScreensNavHost
import com.tommunyiri.saftechweather.presentation.ui.theme.SafTechWeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val locationRequestLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                mainViewModel.processIntent(MainViewIntent.CheckLocationSettings(isEnabled = true))
            } else {
                mainViewModel.processIntent(MainViewIntent.CheckLocationSettings(isEnabled = false))
            }
        }
    private val permissionRequestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            mainViewModel.processIntent(MainViewIntent.GrantPermission(isGranted = isGranted))
        }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        createLocationRequest(
            activity = this,
            locationRequestLauncher = locationRequestLauncher,
        ) {
            mainViewModel.processIntent(MainViewIntent.CheckLocationSettings(isEnabled = true))
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        mainViewModel.processIntent(MainViewIntent.GetTheme)

        setContent {
            val state = mainViewModel.state.collectAsStateWithLifecycle().value
            val shouldUseDarkTheme = when (state.theme) {
                "Light" -> false
                "Dark" -> true
                else -> isSystemInDarkTheme()
            }
            var darkModeState by remember { mutableStateOf(shouldUseDarkTheme) }
            SafTechWeatherTheme(darkTheme = darkModeState) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        CheckForPermissions(
                            onPermissionGranted = {
                                mainViewModel.processIntent(MainViewIntent.GrantPermission(isGranted = true))
                            },
                            onPermissionDenied = {
                                OnPermissionDenied(activityPermissionResult = permissionRequestLauncher)
                            },
                        )

                        InitMainScreen(state, onThemeUpdated = {
                            mainViewModel.processIntent(MainViewIntent.GetTheme)
                            if (shouldUseDarkTheme != darkModeState) {
                                darkModeState.let {
                                    darkModeState = !it
                                }
                            }
                        })
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Composable
    private fun InitMainScreen(state: MainViewState, onThemeUpdated: () -> Unit) {
        when {
            state.isLocationSettingEnabled && state.isPermissionGranted -> {
                fusedLocationProviderClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.run {
                            mainViewModel.processIntent(
                                MainViewIntent.ReceiveLocation(
                                    longitude = location.longitude,
                                    latitude = location.latitude,
                                ),
                            )
                        }
                    }
                WeatherAppScreensNavHost(navController = rememberNavController(), onThemeUpdated)
            }

            state.isLocationSettingEnabled && !state.isPermissionGranted -> {
                RequiresPermissionsScreen()
            }

            !state.isLocationSettingEnabled && !state.isPermissionGranted -> {
                EnableLocationSettingScreen()
            }

            else -> LoadingIndicator()
        }
    }
}

@Composable
private fun shouldUseDarkTheme(theme: String): Boolean {
    return when (theme) {
        "Light" -> false
        "Dark" -> true
        else -> isSystemInDarkTheme()
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SafTechWeatherTheme {
        Greeting("Android")
    }
}
