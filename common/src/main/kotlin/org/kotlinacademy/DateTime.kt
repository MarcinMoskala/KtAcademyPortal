package org.kotlinacademy

expect class DateTime: Comparable<DateTime> {
    val secondOfDay: Int
    val minuteOfDay: Int
    val hourOfDay: Int
    val dayOfMonth: Int
    val monthOfYear: Int
    val year: Int
    fun toDateFormatString(): String
}

// Expects default format
expect fun String.parseDateTime(): DateTime

const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"