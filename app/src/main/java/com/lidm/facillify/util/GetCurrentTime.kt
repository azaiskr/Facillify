package com.lidm.facillify.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDateTime(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("id", "ID"))
    return current.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDate(): LocalDate {
    return LocalDate.now()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getYesterdayDate(): LocalDate {
    return LocalDate.now().minusDays(1)
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("id", "ID"))
    return date.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun parseDate(dateStr: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))
    return LocalDate.parse(dateStr, formatter)
}