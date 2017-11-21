package com.marcinmoskala.kotlinacademy.data

import kotlinx.serialization.Serializable

@Serializable
data class NewsData(
        val news: List<News>
)