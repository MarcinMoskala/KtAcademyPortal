package org.kotlinacademy.data

import org.kotlinacademy.DateTimeSerializer
import org.kotlinacademy.DateTime
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