package com.tommunyiri.saftechweather.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NetworkCondition(
    val code: Int,
    val icon: String,
    val text: String,
) : Parcelable
