package org.kotlinacademy.backend.repositories.db

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.*

interface InfoDatabaseRepository {

    suspend fun getInfos(): List<Info>
    suspend fun getAcceptedInfos(): List<Info>
    suspend fun getInfo(id: Int): Info
    suspend fun addInfo(info: InfoData, isAccepted: Boolean): Info
    suspend fun deleteInfo(id: Int)
    suspend fun updateInfo(info: Info)

    companion object: Provider<InfoDatabaseRepository>() {
        override fun create(): InfoDatabaseRepository = Database.infoDatabase
    }
}