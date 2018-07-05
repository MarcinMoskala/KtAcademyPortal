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
val Puzzler.actualQuestion get() = data.actualQuestion
val Puzzler.codeQuestion get() = data.codeQuestion
val Puzzler.answers get() = data.answers
val Puzzler.correctAnswer get() = data.correctAnswer
val Puzzler.explanation get() = data.explanation
val Puzzler.author get() = data.author
val Puzzler.authorUrl get() = data.authorUrl
val Puzzler.tag get() = "puzzler-$id"
fun Puzzler.getTagUrl(baseUrl: String = "http://portal.kotlin-academy.com/") = "$baseUrl#/?tag=$tag"

@Serializable
data class PuzzlerData(
        val title: String,
        val level: String?,
        val actualQuestion: String,
        val codeQuestion: String,
        val question: String = codeQuestion, // DEPRICATED
        val answers: String,
        val correctAnswer: String,
        val explanation: String,
        val author: String?,
        val authorUrl: String?
)
