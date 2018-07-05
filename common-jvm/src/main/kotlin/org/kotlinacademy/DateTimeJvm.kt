package org.kotlinacademy

import org.joda.time.ReadableDateTime
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.*
import org.joda.time.DateTime as JodaDateTime

actual class DateTime(private val date: JodaDateTime) : Comparable<DateTime> {

    constructor(millis: Long) : this(JodaDateTime(millis))

    constructor(sec: Int, minute: Int, hour: Int, day: Int, month: Int, year: Int) : this(JodaDateTime(year, month, day, hour, minute, sec))

    actual val second: Int
        get() = date.secondOfDay
    actual val minute: Int
        get() = date.minuteOfDay
    actual val hour: Int
        get() = date.hourOfDay
    actual val dayOfMonth: Int
        get() = date.dayOfMonth
    actual val monthOfYear: Int
        get() = date.monthOfYear
    actual val year: Int
        get() = date.year

    actual fun toDateFormatString(): String = date.toString(format)

    override operator fun compareTo(other: DateTime): Int = date.compareTo(other.date)

    override fun toString(): String = toDateFormatString()

    override fun equals(other: Any?): Boolean = other is DateTime && other.date == date

    override fun hashCode(): Int = date.hashCode()

    fun plusMinutes(minute: Int) = DateTime(date.plusMinutes(minute))

    fun minusMinutes(minute: Int) = plusMinutes(-minute)

    fun plusDays(days: Int) = DateTime(date.plusDays(days))

    fun minusDays(days: Int) = plusDays(-days)

    actual operator fun plus(millis: Long) = DateTime(date + millis)
}


actual fun String.parseDateTime() = DateTime(format.parseDateTime(this))

private val format = DateTimeFormat.forPattern(DATE_FORMAT)

actual val now
    get() = DateTime(JodaDateTime())