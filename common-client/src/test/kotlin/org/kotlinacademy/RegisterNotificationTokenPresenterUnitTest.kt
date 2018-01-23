package org.kotlinacademy

import org.kotlinacademy.data.FirebaseTokenType
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenPresenter
import org.kotlinacademy.presentation.notifications.RegisterNotificationTokenView
import org.kotlinacademy.respositories.NotificationRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RegisterNotificationTokenPresenterUnitTest {

    @BeforeTest
    fun setUp() {
        overrideNotificationRepository { _, _ -> }
    }

    @JsName("tokenSendingTest")
    @Test
    fun `Correctly sends token`() {
        val view = RegisterNotificationTokenView()
        overrideNotificationRepository { _, _ -> /* no-op */ }
        val presenter = RegisterNotificationTokenPresenter(view, FAKE_TOKEN_TYPE)
        // When
        presenter.onRefresh(FAKE_TOKEN)
        // Then
        assertEquals(0, view.loggedErrors.size)
    }

    @JsName("errorsLoggingTest")
    @Test
    fun `When repository returns error, it is logged`() {
        val view = RegisterNotificationTokenView()
        overrideNotificationRepository { _, _ -> throw NORMAL_ERROR }
        val presenter = RegisterNotificationTokenPresenter(view, FAKE_TOKEN_TYPE)
        // When
        presenter.onRefresh(FAKE_TOKEN)
        // Then
        assertEquals(1, view.loggedErrors.size)
        assertEquals(NORMAL_ERROR, view.loggedErrors[0])
    }

    @JsName("tokenRegisteredSettingTest")
    @Test
    fun `It is known when token is correctly registered`() {
        val view = RegisterNotificationTokenView()
        overrideNotificationRepository { _, _ -> /* no-op */ }
        val presenter = RegisterNotificationTokenPresenter(view, FAKE_TOKEN_TYPE)
        // When
        presenter.onRefresh(FAKE_TOKEN)
        // Then
        assertTrue(view.tokenRegistered)
    }

    private fun overrideNotificationRepository(onAddFeedback: (String, FirebaseTokenType) -> Unit) {
        NotificationRepository.override = object : NotificationRepository {
            override suspend fun registerToken(token: String, type: FirebaseTokenType) {
                onAddFeedback(token, type)
            }
        }
    }

    private fun RegisterNotificationTokenView() = object : RegisterNotificationTokenView {
        var loggedErrors = listOf<Throwable>()
        var tokenRegistered = false

        override fun setTokenRegistered() {
            tokenRegistered = true
        }

        override fun logError(error: Throwable) {
            loggedErrors += error
        }
    }

    companion object {
        val FAKE_TOKEN = "Token"
        val FAKE_TOKEN_TYPE = FirebaseTokenType.Android
        val NORMAL_ERROR = Error()
    }
}