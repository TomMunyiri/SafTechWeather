package com.tommunyiri.saftechweather.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Created by Tom Munyiri on 30/12/2024.
 * Company:
 * Email:
 */

@Composable
fun WeatherDataStatusText(
    text: String,
    textColor: Color,
    borderColor: Color,
    backgroundColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) // Adds margin (outer spacing)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
            .background(backgroundColor, RoundedCornerShape(10.dp))
            .padding(10.dp, 3.dp, 10.dp, 3.dp), contentAlignment = Alignment.Center
    ) {
        Text(
            text = text, color = textColor, textAlign = TextAlign.Center, fontSize = 12.sp
        )
    }
}