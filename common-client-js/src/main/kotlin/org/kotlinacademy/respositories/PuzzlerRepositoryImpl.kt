package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.httpPost
import org.kotlinacademy.json

class PuzzlerRepositoryImpl : PuzzlerRepository {
    override suspend fun propose(puzzler: Puzzler) {
        httpPost(json.stringify(puzzler), "${Endpoints.puzzler}/${Endpoints.propose}")
    }
}