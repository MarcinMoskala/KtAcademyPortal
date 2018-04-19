package org.kotlinacademy.respositories

import kotlinx.coroutines.experimental.Deferred
import org.kotlinacademy.Endpoints.info
import org.kotlinacademy.Endpoints.propose
import org.kotlinacademy.common.HttpError
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.InfoData
import retrofit2.HttpException
import retrofit2.http.Body
import retrofit2.http.POST

class InfoRepositoryImpl : InfoRepository {

    private val api = retrofit.create(Api::class.java)!!

    override suspend fun propose(infoData: InfoData) {
        try {
            api.propose(infoData).await()
        } catch (t: HttpException) {
            throw HttpError(t.code(), t.message())
        }
    }

    override suspend fun update(info: Info, secret: String) {
        TODO("not implemented") // Implement when it will be used
    }

    interface Api {

        @POST("$info/$propose")
        fun propose(@Body info: InfoData): Deferred<String>
    }
}