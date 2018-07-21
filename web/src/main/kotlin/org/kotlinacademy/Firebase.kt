package org.kotlinacademy

import kotlinx.coroutines.experimental.DefaultDispatcher
import org.kotlinacademy.data.FirebaseTokenType
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenPresenter
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenView
import org.kotlinacademy.respositories.NotificationRepositoryImpl
import kotlin.browser.window

external val firebase: dynamic

class RegisterNotificationTokenService : RegisterNotificationTokenView {

    private val notificationRepository = NotificationRepositoryImpl()
    private val notificationPresenter by lazy {
        RegisterNotificationTokenPresenter(
                uiContext = DefaultDispatcher,
                view = this,
                type = FirebaseTokenType.Web,
                tokenRepository = notificationRepository
        )
    }

    private var tokenSentToServer: Boolean
        get() = window.localStorage.getItem("tokenSentToServer") == "1"
        set(sent) {
            window.localStorage.setItem("tokenSentToServer", if (sent) "1" else "0")
        }

    @Suppress("unused")
    fun initFirebase() {
        firebase.initializeApp(object {
            val apiKey = "AIzaSyCA6efb9eL7J-8MgjGedFe0U7fTno5zhv4"
            val authDomain = "kotlinacademy-d9d13.firebaseapp.com"
            val databaseURL = "https://kotlinacademy-d9d13.firebaseio.com"
            val projectId = "kotlinacademy-d9d13"
            val storageBucket = "kotlinacademy-d9d13.appspot.com"
            val messagingSenderId = "1091715558873"
        })

        val messaging = firebase.messaging()

        messaging.requestPermission()
                .then { setUpToken(messaging) }
                .catch(console::log)

        messaging.onTokenRefresh {
            messaging.getToken()
                    .then { refreshedToken ->
                        notificationPresenter.onRefresh(refreshedToken)
                    }
                    .catch(console::log)
        }
    }

    override fun logError(error: Throwable) {
        console.log(error)
    }

    override fun setTokenRegistered(token: String) {
        tokenSentToServer = true
    }

    private fun setUpToken(messaging: dynamic) {
        messaging.getToken()
                .then { currentToken: String? ->
                    when {
                        currentToken.isNullOrBlank() -> tokenSentToServer = false
                        !tokenSentToServer -> notificationPresenter.onRefresh(currentToken!!)
                    }
                }
                .catch(console::log)
    }
}