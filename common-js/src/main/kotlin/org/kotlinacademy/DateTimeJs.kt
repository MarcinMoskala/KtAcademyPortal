package org.kotlinacademy

import kotlin.js.Date

actual data class DateTime(
        actual val second: Int,
        actual val minute: Int,
        actual val hour: Int,
        actual val dayOfMonth: Int,
        actual val monthOfYear: Int,
        actual val year: Int
) : Comparable<DateTime> {

    actual fun toDateFormatString(): String = DATE_FORMAT
            .replace("yyyy", year.toString(4))
            .replace("MM", monthOfYear.toString(2))
            .replace("dd", dayOfMonth.toString(2))
            .replace("'T'", "T")
            .replace("HH", hour.toString(2))
            .replace("mm", minute.toString(2))
            .replace("ss", second.toString(2))

    override operator fun compareTo(other: DateTime): Int = compareValuesBy(this, other, DateTime::year, DateTime::monthOfYear, DateTime::dayOfMonth, DateTime::hour, DateTime::minute, DateTime::second)

    actual operator fun plus(millis: Long): DateTime {
        val date = Date(year, monthOfYear, dayOfMonth, hour, minute, second)
        val newDate = Date(date.getUTCMilliseconds() + millis)
        return DateTime(newDate.getSeconds(), newDate.getMinutes(), newDate.getHours(), newDate.getDay(), newDate.getMonth(), newDate.getFullYear())
    }
}

actual fun String.parseDateTime() = DateTime(
        substring(17, 19).toInt(),
        substring(14, 16).toInt(),
        substring(11, 13).toInt(),
        substring(8, 10).toInt(),
        substring(5, 7).toInt(),
        substring(0, 4).toInt()
)

private fun Int.toString(minSize: Int): String {
    val str = toString()
    return if (str.length >= minSize) str
    else generateSequence { "0" }.take(minSize - str.length).joinToString() + str
}

actual val now: DateTime
    get() {
        val date = Date(Date.now())
        return DateTime(date.getSeconds(), date.getMinutes(), date.getHours(), date.getDay(), date.getMonth(), date.getFullYear())
    }