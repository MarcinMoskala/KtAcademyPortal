package com.marcinmoskala.kotlinacademy.backend.usecases

import com.marcinmoskala.kotlinacademy.backend.repositories.db.DatabaseRepository
import com.marcinmoskala.kotlinacademy.data.FirebaseTokenData

suspend fun addToken(tokenData: FirebaseTokenData, databaseRepository: DatabaseRepository) {
    databaseRepository.addToken(tokenData.token, tokenData.type)
}

suspend fun getTokenData(databaseRepository: DatabaseRepository): List<FirebaseTokenData>
        = databaseRepository.getAllTokens()