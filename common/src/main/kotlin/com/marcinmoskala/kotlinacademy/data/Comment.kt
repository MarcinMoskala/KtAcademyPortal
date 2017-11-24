package com.marcinmoskala.kotlinacademy.data

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
        val newsId: Int?,
        val rating: Int, // 0-10
        val comment: String,
        val suggestions: String
)