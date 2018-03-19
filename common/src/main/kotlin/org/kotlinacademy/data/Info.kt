package org.kotlinacademy.data

import kotlinx.serialization.Serializable
import org.kotlinacademy.DateTime

@Serializable
data class Info(
        val id: Int,
        val data: InfoData,
        val dateTime: DateTime,
        val accepted: Boolean
)

val Info.title get() = data.title
val Info.imageUrl get() = data.imageUrl
val Info.description get() = data.description
val Info.sources get() = data.sources
val Info.url get() = data.url
val Info.author get() = data.author
val Info.authorUrl get() = data.authorUrl

@Serializable
data class InfoData(
        val title: String,
        val imageUrl: String,
        val description: String,
        val sources: String,
        val url: String?,
        val author: String?,
        val authorUrl: String?
)