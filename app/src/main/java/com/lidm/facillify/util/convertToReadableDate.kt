package com.lidm.facillify.util

import java.text.SimpleDateFormat
import java.util.*

fun convertToReadableDate(dateString: String): String {
    return if (dateString.isEmpty()) {
        "Invalid date"
    } else {
        try {
            // Define the input and output date formats
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))

            // Parse the input date string to a Date object
            val date: Date = inputFormat.parse(dateString) ?: return "Invalid date"

            // Format the Date object to the desired output format
            outputFormat.format(date)
        } catch (e: Exception) {
            "Invalid date"
        }
    }
}
