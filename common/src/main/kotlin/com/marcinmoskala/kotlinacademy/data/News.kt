package com.marcinmoskala.kotlinacademy.data

import com.marcinmoskala.kotlinacademy.DateTimeSerializer
import com.marcinmoskala.kotlinacademy.DateTime
import kotlinx.serialization.Serializable

@Serializable
data class News(
        val id: Int? = null, // Null when proposition
        val title: String,
        val subtitle: String,
        val imageUrl: String,
        val url: String?,
        val occurrence: DateTime
)