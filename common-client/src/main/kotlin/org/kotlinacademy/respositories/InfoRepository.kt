package org.kotlinacademy.respositories

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.InfoData

interface InfoRepository {

    suspend fun propose(info: InfoData)

    companion object : Provider<InfoRepository>() {
        override fun create(): InfoRepository = RepositoriesProvider.getInfoRepository()
    }
}