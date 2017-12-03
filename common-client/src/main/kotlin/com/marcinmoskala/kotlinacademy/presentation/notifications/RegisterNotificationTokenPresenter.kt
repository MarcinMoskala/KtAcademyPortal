package com.marcinmoskala.kotlinacademy.presentation.notifications

import com.marcinmoskala.kotlinacademy.common.delay
import com.marcinmoskala.kotlinacademy.common.launchUI
import com.marcinmoskala.kotlinacademy.data.FirebaseTokenType
import com.marcinmoskala.kotlinacademy.presentation.BasePresenter
import com.marcinmoskala.kotlinacademy.respositories.NotificationRepository
import com.marcinmoskala.kotlinacademy.usecases.PeriodicCaller

class RegisterNotificationTokenPresenter(private val view: RegisterNotificationTokenView, private val type: FirebaseTokenType) : BasePresenter() {

    private val tokenRepository by NotificationRepository.lazyGet()

    fun onRefresh(token: String) {
        jobs += launchUI {
            try {
                tokenRepository.registerToken(token, type)
            } catch (e: Throwable) {
                view.logError(e)
            }
        }
    }
}