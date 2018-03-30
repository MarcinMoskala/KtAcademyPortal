package org.kotlinacademy.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.kotlinacademy.DateTime

@Serializable
data class Puzzler(
        val id: Int,
        val data: PuzzlerData,
        override val dateTime: DateTime,
        val accepted: Boolean
) : News {
    @Transient val title get() = data.title
    @Transient val level get() = data.level
    @Transient val question get() = data.question
    @Transient val answers get() = data.answers
    @Transient val correctAnswer get() = data.correctAnswer
    @Transient val explanation get() = data.explanation
    @Transient val author get() = data.author
    @Transient val authorUrl get() = data.authorUrl
    @Transient val tag get() = "puzzler-$id"
    fun getTagUrl(baseUrl: String = "http://portal.kotlin-academy.com/") = "$baseUrl#/?tag=$tag"
}