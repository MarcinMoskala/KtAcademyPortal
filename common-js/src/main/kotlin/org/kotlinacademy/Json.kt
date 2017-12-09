package org.kotlinacademy

import kotlinx.serialization.SerialContext
import kotlinx.serialization.json.JSON

private val jsonContext = SerialContext()
        .apply { registerSerializer(DateTime::class, DateTimeSerializer) }

val json = JSON(context = jsonContext)