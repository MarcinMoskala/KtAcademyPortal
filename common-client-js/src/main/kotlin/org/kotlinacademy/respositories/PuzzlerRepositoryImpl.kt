package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.Endpoints.propose
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.data.PuzzlerData
import org.kotlinacademy.httpPost
import org.kotlinacademy.json

class PuzzlerRepositoryImpl : PuzzlerRepository {
    override suspend fun propose(puzzlerData: PuzzlerData) {
        httpPost("${Endpoints.puzzler}/$propose", json.stringify(puzzlerData))
    }

    override suspend fun update(puzzler: Puzzler, secret: String) {
        httpPost("${Endpoints.puzzler}?Secret-hash=$secret", json.stringify(puzzler))
    }
}