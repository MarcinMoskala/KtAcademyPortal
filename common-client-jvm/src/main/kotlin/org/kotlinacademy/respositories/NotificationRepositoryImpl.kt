package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.common.HttpError
import org.kotlinacademy.data.FirebaseTokenType
import org.kotlinacademy.data.FirebaseTokenData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.http.Body
import retrofit2.http.POST
import ru.gildor.coroutines.retrofit.await

class NotificationRepositoryImpl : NotificationRepository {

    private val api = retrofit.create(Api::class.java)!!

    suspend override fun registerToken(token: String, type: FirebaseTokenType) {
        try {
            val data = FirebaseTokenData(token, type)
            api.registerFirebaseToken(data).await()
        } catch (t: HttpException) {
            throw HttpError(t.code(), t.message())
        }
    }

    interface Api {

        @POST("${Endpoints.notification}/${Endpoints.notificationRegister}")
        fun registerFirebaseToken(@Body data: FirebaseTokenData): Call<ResponseBody>
    }
}