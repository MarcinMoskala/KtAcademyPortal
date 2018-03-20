package org.kotlinacademy.data

import kotlinx.serialization.Serializable

@Serializable
data class NewsData(
        val articles: List<Article>,
        val infos: List<Info>,
        val puzzlers: List<Puzzler>
) {
    fun allNews(): List<News> = articles + infos + puzzlers
}