package org.kotlinacademy.backend.common

import org.kotlinacademy.DateTime
import org.kotlinacademy.now
import java.util.*

fun DateTime.isToday(): Boolean {
    val now = now
    return dayOfMonth == now.dayOfMonth && monthOfYear == now.monthOfYear && year == now.year
}

fun DateTime.toDate() = Date(year, monthOfYear, dayOfMonth, hour, minute, second)