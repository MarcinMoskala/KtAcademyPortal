package com.marcinmoskala.kotlinacademy.data

import kotlinx.serialization.Serializable

@Serializable
data class CommentsData(
        val comments: List<Comment>
)