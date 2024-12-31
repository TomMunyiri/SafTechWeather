package com.tommunyiri.saftechweather.domain.usecases

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * Created by Tom Munyiri on 31/12/2024.
 * Company:
 * Email:
 */
class DetermineIfWeatherUpdatedUseCase {
    operator fun invoke(lastUpdated: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        return try {
            // Parse both dates
            val lastUpdatedDate = dateFormat.parse(lastUpdated)
            val currentDate = dateFormat.parse(dateFormat.format(Date(System.currentTimeMillis())))

            // Calculate the time difference in milliseconds
            val differenceInMillis = currentDate.time - lastUpdatedDate.time

            // Convert milliseconds to hours
            val differenceInHours = differenceInMillis / (1000 * 60 * 60)

            // Return true if difference is less than or equal to 1 hour
            differenceInHours < 1
        } catch (e: Exception) {
            e.printStackTrace()
            false // Return false if parsing fails
        }
    }
}