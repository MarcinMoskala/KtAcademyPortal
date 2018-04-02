package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.Endpoints.info
import org.kotlinacademy.Endpoints.propose
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.InfoData
import org.kotlinacademy.httpPost
import org.kotlinacademy.json

class InfoRepositoryImpl : InfoRepository {
    override suspend fun propose(infoData: InfoData) {
        httpPost("$info/$propose", json.stringify(infoData))
    }

    override suspend fun update(info: Info, secret: String) {
        httpPost("${Endpoints.info}?Secret-hash=$secret", json.stringify(info))
    }
}