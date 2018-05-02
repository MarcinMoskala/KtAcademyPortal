package org.kotlinacademy

import java.text.SimpleDateFormat
import java.util.*

actual class DateTime(private val date: Date) : Comparable<DateTime> {

    constructor(millis: Long) : this(Date(millis))

    constructor(sec: Int, minute: Int, hour: Int, day: Int, month: Int, year: Int) : this(Date(year, month, day, hour, minute, sec))

    actual fun toDateFormatString(): String = format.format(date)

    override operator fun compareTo(other: DateTime): Int = date.compareTo(other.date)

    override fun toString(): String = toDateFormatString()

    override fun equals(other: Any?): Boolean = other is DateTime && other.date.time == date.time

    override fun hashCode(): Int = date.hashCode()

    fun plusMinutes(minute: Int) = DateTime(date.time + minute * 60 * 1000)

    fun minusMinutes(minute: Int) = plusMinutes(-minute)

    fun plusDays(days: Int) = DateTime(date.time + days * 24 * 60 * 60 * 1000)

    fun toDate() = date
}


actual fun String.parseDateTime() = DateTime(format.parse(this))

private val format = SimpleDateFormat(DATE_FORMAT, Locale.UK)

val now = DateTime(Date())