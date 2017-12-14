package org.kotlinacademy.data

import kotlinx.serialization.Serializable
import org.kotlinacademy.DateTime

@Serializable
data class News(
        val id: Int? = null, // Null when proposition
        val title: String,
        val subtitle: String,
        val imageUrl: String,
        val url: String?,
        val occurrence: DateTime
)