package com.tommunyiri.saftechweather.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tommunyiri.saftechweather.R

/**
 * Created by Tom Munyiri on 06/12/2024.
 * Company:
 * Email:
 */

@Composable
fun SettingsItem(
    itemLabel: String,
    itemValue: String? = null,
    //@DrawableRes itemIcon: Int,
    itemIcon: ImageVector,
    itemIconContentDescription: String,
    onItemClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClicked() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        /*Image(
            painter = painterResource(id = itemIcon),
            contentDescription = itemIconContentDescription,
            modifier = Modifier.padding(8.dp)
        )*/
        Icon(
            imageVector = itemIcon,
            contentDescription = itemIconContentDescription
        )
        Spacer(modifier = Modifier.width(25.dp))
        Column {
            Text(
                text = itemLabel,
                modifier = Modifier.padding(4.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            itemValue?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(3.dp),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }
    }
}