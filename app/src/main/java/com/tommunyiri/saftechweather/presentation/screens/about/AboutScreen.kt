package com.tommunyiri.saftechweather.presentation.screens.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.tommunyiri.saftechweather.R
import com.tommunyiri.saftechweather.presentation.components.TopAppBarComponent


/**
 * Created by Tom Munyiri on 06/12/2024.
 * Company:
 * Email:
 */

@Composable
fun AboutScreen(
    onBackButtonClicked: () -> Unit,
) {
    Column {
        TopAppBarComponent(
            title = stringResource(id = R.string.settings_about),
            onBackButtonClick = onBackButtonClicked,
        )

        LibrariesContainer(
            Modifier.fillMaxSize()
        )
    }
}