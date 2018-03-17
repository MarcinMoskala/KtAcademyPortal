package org.kotlinacademy.data

import kotlinx.serialization.Serializable
import org.kotlinacademy.DateTime

@Serializable
data class Article(
        val id: Int = -1, // -1 when proposition
        val title: String,
        val subtitle: String,
        val imageUrl: String,
        val url: String?,
        val occurrence: DateTime
)