package org.kotlinacademy.respositories

import org.kotlinacademy.common.Provider
import org.kotlinacademy.data.Info

interface InfoRepository {

    suspend fun propose(info: Info)

    companion object : Provider<InfoRepository>() {
        override fun create(): InfoRepository = RepositoriesProvider.getInfoRepository()
    }
}