package org.kotlinacademy.data

import kotlinx.serialization.Serializable

@Serializable
data class NewsData(
        val articles: List<Article> = emptyList(),
        val infos: List<Info> = emptyList(),
        val puzzlers: List<Puzzler> = emptyList()
)

fun NewsData.news() = articles + infos + puzzlers