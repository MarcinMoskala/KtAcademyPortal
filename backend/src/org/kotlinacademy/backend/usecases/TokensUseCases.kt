package org.kotlinacademy.backend.usecases

import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.data.FirebaseTokenData

suspend fun addToken(tokenData: FirebaseTokenData, databaseRepository: DatabaseRepository) {
    databaseRepository.addToken(tokenData.token, tokenData.type)
}

suspend fun getTokenData(databaseRepository: DatabaseRepository): List<FirebaseTokenData>
        = databaseRepository.getAllTokens()