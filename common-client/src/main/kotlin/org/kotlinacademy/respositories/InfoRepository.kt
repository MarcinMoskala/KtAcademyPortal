package org.kotlinacademy.respositories

import org.kotlinacademy.data.Info
import org.kotlinacademy.data.InfoData

interface InfoRepository {

    suspend fun propose(infoData: InfoData)
    suspend fun update(info: Info, secret: String)
}