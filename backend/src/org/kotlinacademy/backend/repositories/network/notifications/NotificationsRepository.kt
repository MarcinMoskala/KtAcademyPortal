package org.kotlinacademy.backend.repositories.network.notifications

import kotlinx.coroutines.experimental.Deferred
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.network.makeRetrofit
import org.kotlinacademy.common.Provider
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationsRepository {

    suspend fun sendNotification(title: String, body: String, icon: String, url: String, token: String): NotificationResult

    class NotificationsRepositoryImpl(private val secretKey: String) : NotificationsRepository {

        private val api: Api = makeRetrofit("https://fcm.googleapis.com/").create(Api::class.java)

        override suspend fun sendNotification(title: String, body: String, icon: String, url: String, token: String) =
                api.pushNotification(
                        authorization = "key=$secretKey",
                        body = PushNotificationData(
                                to = token,
                                notification = NotificationData(
                                        title = title,
                                        body = body,
                                        icon = icon,
                                        click_action = url
                                )
                        )
                ).await()
    }

    interface Api {

        @Headers("Content-Type: application/json")
        @POST("fcm/send")
        fun pushNotification(
                @Header("Authorization") authorization: String,
                @Body body: PushNotificationData
        ): Deferred<NotificationResult>
    }

    companion object : Provider<NotificationsRepository?>() {
        override fun create(): NotificationsRepository? = Config.firebaseSecretApiKey?.let(NotificationsRepository::NotificationsRepositoryImpl)
    }
}