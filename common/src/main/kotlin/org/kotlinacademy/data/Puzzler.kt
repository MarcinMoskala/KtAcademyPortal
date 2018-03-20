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
val Puzzler.question get() = data.question
val Puzzler.answers get() = data.answers
val Puzzler.author get() = data.author
val Puzzler.authorUrl get() = data.authorUrl

@Serializable
data class PuzzlerData(
        val title: String,
        val question: String,
        val answers: String,
        val author: String?,
        val authorUrl: String?
)
