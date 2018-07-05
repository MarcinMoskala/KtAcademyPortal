package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints.accept
import org.kotlinacademy.Endpoints.acceptImportant
import org.kotlinacademy.Endpoints.info
import org.kotlinacademy.Endpoints.moveTop
import org.kotlinacademy.Endpoints.news
import org.kotlinacademy.Endpoints.propositions
import org.kotlinacademy.Endpoints.puzzler
import org.kotlinacademy.Endpoints.reject
import org.kotlinacademy.data.NewsData
import org.kotlinacademy.httpGet
import org.kotlinacademy.httpPost
import org.kotlinacademy.json

class ManagerRepositoryImpl : ManagerRepository {

    override suspend fun acceptInfo(id: Int, secret: String) {
        httpPost("$info/$id/$accept?Secret-hash=$secret")
    }

    override suspend fun acceptPuzzler(id: Int, secret: String) {
        httpPost("$puzzler/$id/$accept?Secret-hash=$secret")
    }

    override suspend fun acceptImportantPuzzler(id: Int, secret: String) {
        httpPost("$puzzler/$id/$acceptImportant?Secret-hash=$secret")
    }

    override suspend fun puzzlerToTop(id: Int, secret: String) {
        httpPost("$puzzler/$id/$moveTop?Secret-hash=$secret")
    }

    override suspend fun rejectInfo(id: Int, secret: String) {
        httpPost("$info/$id/$reject?Secret-hash=$secret")
    }

    override suspend fun rejectPuzzler(id: Int, secret: String) {
        httpPost("$puzzler/$id/$reject?Secret-hash=$secret")
    }

    override suspend fun getPropositions(secret: String): NewsData =
            json.parse(httpGet("$news/$propositions?Secret-hash=$secret"))
}