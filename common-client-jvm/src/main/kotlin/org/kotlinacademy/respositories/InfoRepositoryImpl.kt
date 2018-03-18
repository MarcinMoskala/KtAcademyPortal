package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints.info
import org.kotlinacademy.Endpoints.propose
import org.kotlinacademy.common.HttpError
import org.kotlinacademy.data.Info
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.http.Body
import retrofit2.http.POST
import ru.gildor.coroutines.retrofit.await

class InfoRepositoryImpl : InfoRepository {

    private val api = retrofit.create(Api::class.java)!!

    override suspend fun propose(info: Info) {
        try {
            api.propose(info).await()
        } catch (t: HttpException) {
            throw HttpError(t.code(), t.message())
        }
    }

    interface Api {

        @POST("$info/$propose")
        fun propose(@Body info: Info): Call<String>
    }
}