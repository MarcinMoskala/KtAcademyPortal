package org.kotlinacademy.mobile.view.notifications

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import org.kotlinacademy.common.di.NotificationRepositoryDi
import org.kotlinacademy.data.FirebaseTokenType.Android
import org.kotlinacademy.mobile.log
import org.kotlinacademy.mobile.view.Prefs
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenPresenter
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenView
import org.kotlinacademy.respositories.NotificationRepository
import org.kotlinacademy.respositories.NotificationRepositoryImpl

class FirebaseIdService : FirebaseInstanceIdService(), RegisterNotificationTokenView {

    private val notificationsRepository by NotificationRepositoryDi.lazyGet()
    private val presenter by lazy { RegisterNotificationTokenPresenter(this, Android, notificationsRepository) }

    override fun onTokenRefresh() {
        val token = FirebaseInstanceId.getInstance().token ?: return
        Log.i("New token", token)
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