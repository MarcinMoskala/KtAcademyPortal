package org.kotlinacademy.data

import org.kotlinacademy.Serializable

@Serializable
data class FirebaseTokenData(
        val token: String,
        val type: FirebaseTokenType
)