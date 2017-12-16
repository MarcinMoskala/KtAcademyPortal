package org.kotlinacademy

import org.kotlinacademy.data.FirebaseTokenType
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenPresenter
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenView
import kotlin.browser.window

external val firebase: dynamic

class RegisterNotificationTokenService : RegisterNotificationTokenView {

    private val notificationsPresenter by lazy { RegisterNotificationTokenPresenter(this, FirebaseTokenType.Web) }

    override fun logError(error: Throwable) {
        console.log(error)
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
                .then({ setUpToken(messaging) })
                .catch({ err -> console.log(err) })

        messaging.onTokenRefresh {
            messaging.getToken()
                    .then({ refreshedToken ->
                        setTokenSentToServer(false)
                        if (!isTokenSentToServer())
                            notificationsPresenter.onRefresh(refreshedToken)
                    })
                    .catch({ err -> console.log(err) })
        }
    }

    private fun setUpToken(messaging: dynamic) {
        messaging.getToken()
                .then({ currentToken: String? ->
                    if (currentToken != null && currentToken.isNotBlank()) {
                        notificationsPresenter.onRefresh(currentToken)
                    } else {
                        setTokenSentToServer(false)
                    }
                })
                .catch({ err ->
                    console.log(err)
                    setTokenSentToServer(false)
                })
    }

    private fun isTokenSentToServer() = window.localStorage.getItem("tokenSentToServer") == "1"

    private fun setTokenSentToServer(sent: Boolean) {
        window.localStorage.setItem("tokenSentToServer", if (sent) "1" else "0")
    }
}