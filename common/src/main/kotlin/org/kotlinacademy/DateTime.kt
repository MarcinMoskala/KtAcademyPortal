package org.kotlinacademy

expect class DateTime: Comparable<DateTime> {
    fun toDateFormatString(): String
}

// Expects default format
expect fun String.parseDateTime(): DateTime

const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
