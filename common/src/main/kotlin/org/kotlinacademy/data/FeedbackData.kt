package org.kotlinacademy.data

import org.kotlinacademy.Serializable

@Serializable
data class FeedbackData(
        val feedbacks: List<Feedback>
)