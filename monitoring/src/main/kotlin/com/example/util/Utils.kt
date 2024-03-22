package com.example.util

import java.time.*
import java.time.format.DateTimeFormatter

fun calculateEndTimestamp(startTimestamp: Instant): Instant =
    startTimestamp.plusMillis(60 * 60 * 1000L)

fun parseDateStringToInstant(dateString: String): Instant {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val localDate = LocalDate.parse(dateString, formatter)
    return localDate.atStartOfDay().toInstant(ZoneOffset.UTC)
}

fun areSameDate(instant1: Instant, instant2: Instant): Boolean {
    val date1 = instant1.atZone(ZoneOffset.UTC).toLocalDate()
    val date2 = instant2.atZone(ZoneOffset.UTC).toLocalDate()
    return date1 == date2
}

fun formatInstantToHourMinute(instant: Instant): String {
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return localDateTime.format(formatter)
}
