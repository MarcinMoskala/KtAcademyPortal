package org.kotlinacademy.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.kotlinacademy.DateTime

@Serializable
data class Info(
        val id: Int,
        val data: InfoData,
        override val dateTime: DateTime,
        val accepted: Boolean
) : News {
    @Transient val title get() = data.title
    @Transient val imageUrl get() = data.imageUrl
    @Transient val description get() = data.description
    @Transient val sources get() = data.sources
    @Transient val url get() = data.url
    @Transient val author get() = data.author
    @Transient val authorUrl get() = data.authorUrl
    @Transient val tag get() = "info-$id"
    fun getTagUrl(baseUrl: String = "http://portal.kotlin-academy.com/") = "$baseUrl#/?tag=$tag"
}