package org.kotlinacademy.data

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseTokenData(
        val token: String,
        val type: FirebaseTokenType
)