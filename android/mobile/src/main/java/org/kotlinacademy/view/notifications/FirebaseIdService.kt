package org.kotlinacademy.view.notifications

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import org.kotlinacademy.BuildConfig
import org.kotlinacademy.data.FirebaseTokenType.Android
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenPresenter
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenView

class FirebaseIdService : FirebaseInstanceIdService(), RegisterNotificationTokenView {

    private val presenter by lazy { RegisterNotificationTokenPresenter(this, Android) }

    override fun onTokenRefresh() {
        val newToken = FirebaseInstanceId.getInstance().token ?: return
        if (BuildConfig.DEBUG) Log.i(TAG, "Refreshed token: " + newToken)
        presenter.onRefresh(newToken)
    }

    override fun logError(error: Throwable) {
        if (BuildConfig.DEBUG) Log.e(TAG, error.message, error)
    }

    override fun onDestroy() {
        presenter.onDestroy()
    }

    companion object {
        val TAG = this::class.simpleName
    }
}