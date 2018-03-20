package org.kotlinacademy.data

import kotlinx.serialization.Serializable
import org.kotlinacademy.DateTime

@Serializable
data class Article(
        val id: Int,
        val data: ArticleData,
        override val dateTime: DateTime = data.occurrence
): News

val Article.title get() = data.title
val Article.subtitle get() = data.subtitle
val Article.imageUrl get() = data.imageUrl
val Article.url get() = data.url
val Article.occurrence get() = data.occurrence

@Serializable
data class ArticleData(
        val title: String,
        val subtitle: String,
        val imageUrl: String,
        val url: String?,
        val occurrence: DateTime
)