package org.kotlinacademy

actual data class DateTime(
        actual val secondOfDay: Int,
        actual val minuteOfDay: Int,
        actual val hourOfDay: Int,
        actual val dayOfMonth: Int,
        actual val monthOfYear: Int,
        actual val year: Int
) : Comparable<DateTime> {

    actual fun toDateFormatString(): String = DATE_FORMAT
            .replace("yyyy", year.toString(4))
            .replace("MM", monthOfYear.toString(2))
            .replace("dd", dayOfMonth.toString(2))
            .replace("'T'", "T")
            .replace("HH", hourOfDay.toString(2))
            .replace("mm", minuteOfDay.toString(2))
            .replace("ss", secondOfDay.toString(2))

    override operator fun compareTo(other: DateTime): Int
            = compareValuesBy(this, other, DateTime::year, DateTime::monthOfYear, DateTime::dayOfMonth, DateTime::hourOfDay, DateTime::minuteOfDay, DateTime::secondOfDay)
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