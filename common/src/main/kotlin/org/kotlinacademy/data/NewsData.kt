package org.kotlinacademy.data

import org.kotlinacademy.Serializable

@Serializable
data class NewsData(
        val articles: List<Article> = emptyList(),
        val infos: List<Info> = emptyList(),
        val puzzlers: List<Puzzler> = emptyList(),
        val snippets: List<Snippet> = emptyList()
)

fun NewsData.news() = articles + infos + puzzlers + snippets