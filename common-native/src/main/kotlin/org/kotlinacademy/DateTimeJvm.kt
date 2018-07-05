package org.kotlinacademy

actual class DateTime() : Comparable<DateTime> {

    actual val second: Int
        get() = 0
    actual val minute: Int
        get() = 0
    actual val hour: Int
        get() = 0
    actual val dayOfMonth: Int
        get() = 0
    actual val monthOfYear: Int
        get() = 0
    actual val year: Int
        get() = 0

    actual fun toDateFormatString(): String = ""

    override operator fun compareTo(other: DateTime): Int = 1

    actual operator fun plus(millis: Long) = DateTime()
}

actual fun String.parseDateTime() = DateTime()

actual val now
    get() = DateTime()