package org.kotlinacademy.data

import org.kotlinacademy.DateTime

data class Puzzler(
        val id: Int, // -1 when proposition
        val title: String,
        val question: String,
        val answers: List<PossibleAnswer>,
        val author: String?,
        val authorUrl: String?,
        val dateTime: DateTime
) {
    data class PossibleAnswer(
            val text: String,
            val correct: Boolean
    )
}