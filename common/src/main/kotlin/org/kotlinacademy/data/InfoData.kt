package org.kotlinacademy.data

import kotlinx.serialization.Serializable

@Serializable
data class InfoData(
        val title: String,
        val imageUrl: String,
        val description: String,
        val sources: String,
        val url: String?,
        val author: String?,
        val authorUrl: String?
)