package org.kotlinacademy.data

import kotlinx.serialization.Serializable

@Serializable
data class PuzzlerData(
        val title: String,
        val level: String?,
        val question: String,
        val answers: String,
        val correctAnswer: String,
        val explanation: String,
        val author: String?,
        val authorUrl: String?
)
