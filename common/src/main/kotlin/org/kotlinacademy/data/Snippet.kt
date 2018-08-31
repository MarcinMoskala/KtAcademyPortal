package org.kotlinacademy.data

import org.kotlinacademy.Serializable
import org.kotlinacademy.DateTime

@Serializable
data class Snippet(
        val id: Int,
        val data: SnippetData,
        override val dateTime: DateTime,
        val accepted: Boolean
) : News

val Snippet.title get() = data.title
val Snippet.code get() = data.code
val Snippet.explanation get() = data.explanation
val Snippet.author get() = data.author
val Snippet.authorUrl get() = data.authorUrl
val Snippet.tag get() = "snippet-$id"
fun Snippet.getTagUrl(baseUrl: String = "http://portal.kotlin-academy.com/") = "$baseUrl#/?tag=$tag"

@Serializable
data class SnippetData(
        val title: String? = null,
        val code: String,
        val explanation: String? = null,
        val author: String?,
        val authorUrl: String?
)
