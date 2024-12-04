package com.tommunyiri.saftechweather.presentation.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tommunyiri.saftechweather.R
import com.tommunyiri.saftechweather.presentation.components.TopAppBarComponent


/**
 * Created by Tom Munyiri on 04/12/2024.
 * Company:
 * Email:
 */

@Composable
fun DetailsScreen(selectedDate: String, onBackButtonClicked: () -> Unit) {
    TopAppBarComponent(
        //title = stringResource(id = R.string.settings_screen_title),
        title = "Weather for $selectedDate",
        onBackButtonClick = onBackButtonClicked
    )
}