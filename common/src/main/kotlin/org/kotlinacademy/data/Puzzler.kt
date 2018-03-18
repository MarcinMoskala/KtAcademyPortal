package org.kotlinacademy.data

import org.kotlinacademy.DateTime

data class Puzzler(
        val id: Int = -1, // -1 when proposition
        val title: String,
        val question: String,
        val answers: String,
        val author: String?,
        val authorUrl: String?,
        val dateTime: DateTime? = null
) {
    data class PossibleAnswer(
            val text: String,
            val correct: Boolean
    )
}