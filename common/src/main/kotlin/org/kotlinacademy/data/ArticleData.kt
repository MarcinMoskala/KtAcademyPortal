package org.kotlinacademy.data

import kotlinx.serialization.Serializable
import org.kotlinacademy.DateTime

@Serializable
data class ArticleData(
        val title: String,
        val subtitle: String,
        val imageUrl: String,
        val url: String?,
        val occurrence: DateTime
)