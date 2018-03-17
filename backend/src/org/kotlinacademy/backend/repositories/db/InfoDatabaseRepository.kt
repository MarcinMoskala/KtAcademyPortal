package org.kotlinacademy.backend.repositories.db

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.*

interface InfoDatabaseRepository {

    suspend fun getInfos(): List<Info>
    suspend fun getAcceptedInfos(): List<Info>
    suspend fun getInfo(id: Int): Info
    suspend fun addInfo(info: Info, isAccepted: Boolean)
    suspend fun deleteInfo(id: Int)
    suspend fun updateInfo(id: Int, info: Info, isAccepted: Boolean? = null)

    companion object: Provider<InfoDatabaseRepository>() {
        override fun create(): InfoDatabaseRepository = Database.infoDatabase
    }
}