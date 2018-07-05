package org.kotlinacademy

expect class DateTime : Comparable<DateTime> {
    val second: Int
    val minute: Int
    val hour: Int
    val dayOfMonth: Int
    val monthOfYear: Int
    val year: Int
    fun toDateFormatString(): String
    operator fun plus(millis: Long): DateTime
}

expect val now: DateTime

// Expects default format
expect fun String.parseDateTime(): DateTime

const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

operator fun DateTime.minus(millis: Long): DateTime = plus(-millis)
