package org.kotlinacademy.backend.repositories.db

import org.kotlinacademy.backend.common.Provider
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType

interface TokenDatabaseRepository {

    suspend fun getAllTokens(): List<FirebaseTokenData>
    suspend fun addToken(token: String, tokenType: FirebaseTokenType)

    companion object: Provider<TokenDatabaseRepository>() {
        override fun create(): TokenDatabaseRepository = Database.tokenDatabase
    }
}