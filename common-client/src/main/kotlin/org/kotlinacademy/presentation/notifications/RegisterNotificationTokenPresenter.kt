package org.kotlinacademy.presentation.notifications

import org.kotlinacademy.common.launchUI
import org.kotlinacademy.data.FirebaseTokenType
import org.kotlinacademy.presentation.BasePresenter
import org.kotlinacademy.respositories.NotificationRepository

class RegisterNotificationTokenPresenter(
        private val view: RegisterNotificationTokenView,
        private val type: FirebaseTokenType
) : BasePresenter() {

    private val tokenRepository by NotificationRepository.lazyGet()

    fun onRefresh(token: String) {
        jobs += launchUI {
            try {
                tokenRepository.registerToken(token, type)
                view.setTokenRegistered()
            } catch (e: Throwable) {
                view.logError(e)
            }
        }
    }
}