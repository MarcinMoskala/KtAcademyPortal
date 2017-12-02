package com.marcinmoskala.kotlinacademy

actual data class DateTime(
        val second: Int,
        val minute: Int,
        val hour: Int,
        val day: Int,
        val month: Int,
        val year: Int
): Comparable<DateTime> {

    actual fun toDateFormatString(): String = DATE_FORMAT
            .replace("yyyy", year.toString(4))
            .replace("MM", month.toString(2))
            .replace("dd", day.toString(2))
            .replace("'T'", "T")
            .replace("HH", hour.toString(2))
            .replace("mm", minute.toString(2))
            .replace("ss", second.toString(2))

    override operator fun compareTo(other: DateTime): Int
            = compareValuesBy(this, other, DateTime::year, DateTime::month, DateTime::day, DateTime::hour, DateTime::minute, DateTime::second)
}

actual fun String.parseDate() = DateTime(
        substring(19,21).toInt(),
        substring(16,18).toInt(),
        substring(13,15).toInt(),
        substring(8,10).toInt(),
        substring(5,7).toInt(),
        substring(0,4).toInt()
)

private fun Int.toString(minSize: Int): String {
    val str = toString()
    return if (str.length >= minSize) str
    else generateSequence { "0" }.take(minSize - str.length).joinToString() + str
}