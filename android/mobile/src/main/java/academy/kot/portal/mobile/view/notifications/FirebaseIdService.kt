package academy.kot.portal.mobile.view.notifications

import academy.kot.portal.android.di.NotificationRepositoryDi
import academy.kot.portal.mobile.log
import academy.kot.portal.mobile.view.Prefs
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import kotlinx.coroutines.experimental.android.UI
import org.kotlinacademy.data.FirebaseTokenType.Android
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenPresenter
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenView

class FirebaseIdService : FirebaseInstanceIdService(), RegisterNotificationTokenView {

    private val notificationsRepository by NotificationRepositoryDi.lazyGet()
    private val presenter by lazy { RegisterNotificationTokenPresenter(UI, this, Android, notificationsRepository) }

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