package com.tommunyiri.saftechweather.domain.model

data class NetworkForecastday(
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<Hour>,
    var modifiedAt: String? = null
)
