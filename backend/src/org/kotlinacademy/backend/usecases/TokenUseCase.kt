package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.repositories.db.TokenDatabaseRepository
import org.kotlinacademy.data.FirebaseTokenData

object TokenUseCase {

    suspend fun register(registerTokenData: FirebaseTokenData) {
        val tokenDatabaseRepository by TokenDatabaseRepository.lazyGet()

        tokenDatabaseRepository.addToken(registerTokenData.token, registerTokenData.type)
    }

    suspend fun getAll(): List<FirebaseTokenData> {
        val tokenDatabaseRepository by TokenDatabaseRepository.lazyGet()

        return tokenDatabaseRepository.getAllTokens()
    }
}