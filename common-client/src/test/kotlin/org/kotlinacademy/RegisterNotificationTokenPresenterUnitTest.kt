package org.kotlinacademy

import org.kotlinacademy.data.FirebaseTokenType
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenPresenter
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenView
import org.kotlinacademy.respositories.NotificationRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RegisterNotificationTokenPresenterUnitTest : BaseUnitTest() {

    @JsName("tokenSendingTest")
    @Test
    fun `Correctly sends token`() {
        // Given
        val view = RegisterNotificationTokenView()
        val repo = notificationRepository { _, _ -> /* no-op */ }
        val presenter = RegisterNotificationTokenPresenter(view, someTokenType, repo)
        // When
        presenter.onRefresh(someToken)
        // Then
        assertEquals(0, view.loggedErrors.size)
    }

    @JsName("errorsLoggingTest")
    @Test
    fun `When repository returns error, it is logged`() {
        // Given
        val view = RegisterNotificationTokenView()
        val repo = notificationRepository { _, _ -> throw someError }
        val presenter = RegisterNotificationTokenPresenter(view, someTokenType, repo)
        // When
        presenter.onRefresh(someToken)
        // Then
        assertEquals(1, view.loggedErrors.size)
        assertEquals(someError, view.loggedErrors[0])
    }

    @JsName("tokenRegisteredSettingTest")
    @Test
    fun `It is known when token is correctly registered`() {
        // Given
        val view = RegisterNotificationTokenView()
        val repo = notificationRepository { _, _ -> /* no-op */ }
        val presenter = RegisterNotificationTokenPresenter(view, someTokenType, repo)
        // When
        presenter.onRefresh(someToken)
        // Then
        assertTrue(view.tokenRegistered)
    }

    private fun notificationRepository(onAddFeedback: (String, FirebaseTokenType) -> Unit) = object : NotificationRepository {
        override suspend fun registerToken(token: String, type: FirebaseTokenType) {
            onAddFeedback(token, type)
        }
    }

    private fun RegisterNotificationTokenView() = object : RegisterNotificationTokenView {
        var loggedErrors = listOf<Throwable>()
        var tokenRegistered = false

        override fun setTokenRegistered(token: String) {
            tokenRegistered = true
        }

        override fun logError(error: Throwable) {
            loggedErrors += error
        }
    }
}