package com.tommunyiri.saftechweather.presentation.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tommunyiri.saftechweather.R
import com.tommunyiri.saftechweather.presentation.components.TopAppBarComponent

/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */

@Composable
fun SettingsScreen(onBackButtonClicked: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBarComponent(
            title = stringResource(id = R.string.settings_screen_title),
            onBackButtonClick = onBackButtonClicked,
        )
    }
}
