package org.kotlinacademy.backend.repositories.db

import org.kotlinacademy.backend.common.Provider
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.InfoData

interface InfoDatabaseRepository {

    suspend fun getInfos(): List<Info>
    suspend fun getInfo(id: Int): Info
    suspend fun addInfo(info: InfoData, isAccepted: Boolean): Info
    suspend fun deleteInfo(id: Int)
    suspend fun updateInfo(info: Info)

    companion object: Provider<InfoDatabaseRepository>() {
        override fun create(): InfoDatabaseRepository = Database.infoDatabase
    }
}