package com.marcinmoskala.kotlinacademy.data

data class Comment(
        val newsId: Int?,
        val rating: Int, // 0-10
        val comment: String,
        val suggestions: String
)