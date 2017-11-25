package com.marcinmoskala.kotlinacademy.data

import kotlinx.serialization.Serializable

@Serializable
data class FeedbackData(
        val feedbacks: List<Feedback>
)