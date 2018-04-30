package org.kotlinacademy.backend.repositories.network.notifications

import kotlinx.coroutines.experimental.Deferred
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.common.Provider
import org.kotlinacademy.backend.repositories.network.makeRetrofit
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface NotificationsRepository {

    suspend fun sendNotification(token: String, notificationData: NotificationData): NotificationResult

    class NotificationsRepositoryImpl(private val secretKey: String) : NotificationsRepository {

        private val api: Api = makeRetrofit("https://fcm.googleapis.com/").create(Api::class.java)

        override suspend fun sendNotification(token: String, notificationData: NotificationData) = try {
            api.pushNotification(
                    authorization = "key=$secretKey",
                    body = PushNotificationData(token, notificationData
                    )
            ).await()
        } catch (t: Throwable) {
            NotificationResult(0, 1)
        }
    }

    interface Api {

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