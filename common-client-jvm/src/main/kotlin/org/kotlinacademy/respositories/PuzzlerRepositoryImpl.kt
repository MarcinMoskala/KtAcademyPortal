package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints.propose
import org.kotlinacademy.Endpoints.puzzler
import org.kotlinacademy.common.HttpError
import org.kotlinacademy.data.Puzzler
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.http.Body
import retrofit2.http.POST
import ru.gildor.coroutines.retrofit.await

class PuzzlerRepositoryImpl : PuzzlerRepository {

    private val api = retrofit.create(Api::class.java)!!

    override suspend fun propose(puzzler: Puzzler) {
        try {
            api.propose(puzzler).await()
        } catch (t: HttpException) {
            throw HttpError(t.code(), t.message())
        }
    }

    interface Api {

        @POST("$puzzler/$propose")
        fun propose(@Body puzzler: Puzzler): Call<String>
    }
}