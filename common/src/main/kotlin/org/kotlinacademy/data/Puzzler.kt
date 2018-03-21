package org.kotlinacademy.data

import kotlinx.serialization.Serializable
import org.kotlinacademy.DateTime

@Serializable
data class Puzzler(
        val id: Int,
        val data: PuzzlerData,
        override val dateTime: DateTime,
        val accepted: Boolean
) : News

val Puzzler.title get() = data.title
val Puzzler.level get() = data.level
val Puzzler.question get() = data.question
val Puzzler.answers get() = data.answers
val Puzzler.correctAnswer get() = data.correctAnswer
val Puzzler.explanation get() = data.explanation
val Puzzler.author get() = data.author
val Puzzler.authorUrl get() = data.authorUrl

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
