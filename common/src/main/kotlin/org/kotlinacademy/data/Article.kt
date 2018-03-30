package org.kotlinacademy.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.kotlinacademy.DateTime

@Serializable
data class Article(
        val id: Int,
        val data: ArticleData,
        override val dateTime: DateTime = data.occurrence
): News {
    @Transient val title get() = data.title
    @Transient val subtitle get() = data.subtitle
    @Transient val imageUrl get() = data.imageUrl
    @Transient val url get() = data.url
    @Transient val occurrence get() = data.occurrence
}