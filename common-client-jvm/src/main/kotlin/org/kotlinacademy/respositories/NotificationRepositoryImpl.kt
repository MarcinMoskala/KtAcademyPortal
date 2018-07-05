package org.kotlinacademy.respositories

import kotlinx.coroutines.experimental.Deferred
import okhttp3.ResponseBody
import org.kotlinacademy.Endpoints
import org.kotlinacademy.common.HttpError
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType
import retrofit2.HttpException
import retrofit2.http.Body
import retrofit2.http.POST

class NotificationRepositoryImpl : NotificationRepository {

    private val api = retrofit.create(Api::class.java)!!

    override suspend fun registerToken(token: String, type: FirebaseTokenType) {
        try {
            val data = FirebaseTokenData(token, type)
            api.registerFirebaseToken(data).await()
        } catch (t: HttpException) {
            throw HttpError(t.code(), t.message())
        }
    }

    interface Api {

        @POST("${Endpoints.notification}/${Endpoints.notificationRegister}")
        fun registerFirebaseToken(@Body data: FirebaseTokenData): Deferred<ResponseBody>
    }
}