package com.tommunyiri.saftechweather.presentation.components

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tommunyiri.saftechweather.R


/**
 * Created by Tom Munyiri on 03/12/2024.
 * Company:
 * Email:
 */

@Composable
fun PermissionRationaleDialog(
    isDialogShown: MutableState<Boolean>,
    activityPermissionResult: ActivityResultLauncher<String>,
    showWeatherUI: MutableState<Boolean>
) {
    Dialog(onDismissRequest = { isDialogShown.value = false }) {
        Surface(
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(modifier = Modifier.padding(15.dp)) {
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(id = R.string.location_rationale_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(text = stringResource(id = R.string.location_rationale_description))
                Spacer(modifier = Modifier.width(10.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Row {
                        Text(
                            text = stringResource(id = R.string.location_rationale_button_deny),
                            modifier =
                                Modifier
                                    .clickable(onClick = {
                                        isDialogShown.value = false
                                        showWeatherUI.value = false
                                    })
                                    .padding(10.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(id = R.string.location_rationale_button_grant),
                            modifier =
                                Modifier
                                    .clickable(onClick = {
                                        isDialogShown.value = false
                                        activityPermissionResult.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                                    })
                                    .padding(10.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}