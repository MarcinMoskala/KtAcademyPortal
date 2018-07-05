package org.kotlinacademy.data

import org.kotlinacademy.Serializable

@Serializable
data class Feedback(
        val newsId: Int?,
        val rating: Int, // 0-10
        val comment: String,
        val suggestions: String
)