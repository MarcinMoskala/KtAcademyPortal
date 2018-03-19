package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.InfoData
import org.kotlinacademy.httpPost
import org.kotlinacademy.json

class InfoRepositoryImpl : InfoRepository {
    override suspend fun propose(info: InfoData) {
        httpPost(json.stringify(info), "${Endpoints.info}/${Endpoints.propose}")
    }
}