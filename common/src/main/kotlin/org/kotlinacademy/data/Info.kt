package org.kotlinacademy.data

import org.kotlinacademy.DateTime

data class Info(
        val id: Int, // -1 when proposition
        val title: String,
        val imageUrl: String,
        val description: String,
        val sources: String,
        val url: String?,
        val author: String?,
        val authorUrl: String?,
        val dateTime: DateTime
)