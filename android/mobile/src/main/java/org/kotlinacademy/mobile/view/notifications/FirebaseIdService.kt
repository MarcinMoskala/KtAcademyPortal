package org.kotlinacademy.mobile.view.notifications

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import org.kotlinacademy.data.FirebaseTokenType.Android
import org.kotlinacademy.mobile.log
import org.kotlinacademy.mobile.view.Prefs
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenPresenter
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenView
import kotlin.concurrent.thread

class FirebaseIdService : FirebaseInstanceIdService(), RegisterNotificationTokenView {

    private val presenter by lazy { RegisterNotificationTokenPresenter(this, Android) }

    override fun onTokenRefresh() {
        val newToken = FirebaseInstanceId.getInstance().token ?: return
        log("Refreshed token: " + newToken)
        presenter.onRefresh(newToken)
    }

    override fun setTokenRegistered() {
        Prefs.tokenSentToServer = true
    }

    override fun logError(error: Throwable) {
        log(error)
    }

    override fun onDestroy() {
        presenter.onDestroy()
    }

    companion object {

        fun ensureThatTokenSent() {
            val token = FirebaseInstanceId.getInstance().token
            if (token != null && !Prefs.tokenSentToServer) {
                forceTokenRefresh()
            }
        }

        private fun forceTokenRefresh() {
            thread {
                FirebaseInstanceId.getInstance().deleteInstanceId()
                FirebaseInstanceId.getInstance().token
            }
        }
    }
}