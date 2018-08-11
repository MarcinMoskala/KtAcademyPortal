package org.kotlinacademy.presentation.notifications

import kotlinx.coroutines.experimental.launch
import org.kotlinacademy.data.FirebaseTokenType
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.NotificationRepository
import kotlin.coroutines.experimental.CoroutineContext

class RegisterNotificationTokenPresenter(
        private val uiContext: CoroutineContext,
        private val view: RegisterNotificationTokenView,
        private val type: FirebaseTokenType,
        private val tokenRepository: NotificationRepository
) : BasePresenter() {

    fun onRefresh(token: String) {
        jobs += launch(uiContext) {
            try {
                tokenRepository.registerToken(token, type)
                view.setTokenRegistered(token)
            } catch (e: Throwable) {
                view.logError(e)
            }
        }
    }
}