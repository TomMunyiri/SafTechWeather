package com.tommunyiri.saftechweather.domain.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String.formatDate(): String? {
    val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val formatter = SimpleDateFormat("EEE, HH:mma", Locale.getDefault())
    return parser.parse(this)?.let { formatter.format(it) }
}
