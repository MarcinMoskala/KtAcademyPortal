package org.kotlinacademy.mobile.view.notifications

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import org.kotlinacademy.data.FirebaseTokenType.Android
import org.kotlinacademy.mobile.log
import org.kotlinacademy.mobile.view.Prefs
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenPresenter
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenView

class FirebaseIdService : FirebaseInstanceIdService(), RegisterNotificationTokenView {

    private val presenter by lazy { RegisterNotificationTokenPresenter(this, Android) }

    override fun onTokenRefresh() {
        val token = FirebaseInstanceId.getInstance().token ?: return
        presenter.onRefresh(token)
    }

    override fun setTokenRegistered(token: String) {
        Prefs.tokenSentToServer = token
    }

    override fun logError(error: Throwable) {
        log(error)
    }

    override fun onDestroy() {
        presenter.onDestroy()
    }
}