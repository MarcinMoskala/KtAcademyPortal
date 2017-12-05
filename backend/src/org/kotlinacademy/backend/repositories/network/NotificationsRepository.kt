package org.kotlinacademy.backend.repositories.network

import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.network.dto.NotificationData
import org.kotlinacademy.backend.repositories.network.dto.PushNotificationData
import org.kotlinacademy.common.Provider
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.gildor.coroutines.retrofit.await

interface NotificationsRepository {

    suspend fun sendNotification(title: String, icon: String, token: String)

    class NotificationsRepositoryImpl(private val secretKey: String) : NotificationsRepository {

        private val api: Api = makeRetrofit("https://fcm.googleapis.com/").create(Api::class.java)

        override suspend fun sendNotification(title: String, icon: String, token: String) {
            api.pushNotification(
                    authorization = "key=$secretKey",
                    body = PushNotificationData(
                            to = token,
                            notification = NotificationData(
                                    title = title,
                                    icon = icon
                            )
                    )
            ).await()
        }
    }

    interface Api {

        @Headers("Content-Type: application/json")
        @POST("fcm/send")
        fun pushNotification(
                @Header("Authorization") authorization: String,
                @Body body: PushNotificationData
        ): Call<ResponseBody>
    }

    companion object : Provider<NotificationsRepository?>() {
        override fun create(): NotificationsRepository? = Config.firebaseSecretApiKey?.let(NotificationsRepository::NotificationsRepositoryImpl)
    }
}