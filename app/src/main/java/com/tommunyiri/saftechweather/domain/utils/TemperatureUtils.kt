package com.tommunyiri.saftechweather.domain.utils

import java.text.DecimalFormat

/**
 * This function converts a [number] from Kelvin to Celsius by using [DecimalFormat] and
 * converting it to a [Double] then subtracting 273 from it.
 * @param number the number to be converted to Celsius.
 */
fun convertKelvinToCelsius(number: Number): Double {
    return DecimalFormat().run {
        applyPattern(".##")
        parse(format(number.toDouble().minus(273)))?.toDouble() ?: 0.00
    }
}
