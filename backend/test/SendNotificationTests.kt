import io.mockk.*
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.network.notifications.NotificationData
import org.kotlinacademy.backend.usecases.NotificationsUseCase
import org.kotlinacademy.data.FirebaseTokenType
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SendNotificationTests : UseCaseTest() {

    val body = "Some text"
    val url = "Some url"

    @Test
    fun `sendEmailWithInfoAboutFeedback sends Web notification with correct title, url, token and type`() = runBlocking {
        val token = "Some token"
        val type = FirebaseTokenType.Web

        coEvery { notificationsRepo.sendNotification(any(), any()) } returns someNotificationResult

        // When
        val result = NotificationsUseCase.send(body, url, token, type)

        // Then
        assertEquals(someNotificationResult, result)
        val slot = slot<NotificationData>()
        coVerify {
            notificationsRepo.sendNotification(token, capture(slot))
        }
        val notificationData = slot.captured
        assertEquals("Kotlin Academy", notificationData.title)
        assertEquals(body, notificationData.body)
        assertEquals("img/logo.png", notificationData.icon)
        assertEquals(url, notificationData.click_action)
    }

    @Test
    fun `sendEmailWithInfoAboutFeedback sends Android notification with correct title, url, token and type`() = runBlocking {
        val token = "Some token"
        val type = FirebaseTokenType.Android

        coEvery { notificationsRepo.sendNotification(any(), any()) } returns someNotificationResult

        // When
        val result = NotificationsUseCase.send(body, url, token, type)

        // Then
        assertEquals(someNotificationResult, result)
        val slot = slot<NotificationData>()
        coVerify {
            notificationsRepo.sendNotification(token, capture(slot))
        }
        val notificationData = slot.captured
        assertEquals("Kotlin Academy", notificationData.title)
        assertEquals(body, notificationData.body)
        assertEquals("icon_notification", notificationData.icon)
        assertNull(notificationData.click_action)
    }

    @Test
    fun `sendNotifications sends notification to all tokens in database`() = runBlocking {
        coEvery { tokenDbRepo.getAllTokens() } returns listOf(someFirebaseTokenData, someFirebaseTokenData2)
        coEvery { notificationsRepo.sendNotification(any(), any()) } returns someNotificationResult

        // When
        NotificationsUseCase.sendToAll(body, url)

        // Then
        coVerify(ordering = Ordering.SEQUENCE) {
            notificationsRepo.sendNotification(someFirebaseTokenData.token, any())
            notificationsRepo.sendNotification(someFirebaseTokenData2.token, any())
        }
    }

    @Test
    fun `sendNotifications sends email with summary `() = runBlocking {
        objectMockk(Config).use {
            every { Config.adminEmail } returns someEmail

            coEvery { tokenDbRepo.getAllTokens() } returns listOf(someFirebaseTokenData, someFirebaseTokenData2)
            coEvery { notificationsRepo.sendNotification(any(), any()) } returnsMany listOf(someNotificationResult, someNotificationResult2)

            // When
            NotificationsUseCase.sendToAll("Some text", "Some url")

            // Then
            val messageSlot = CapturingSlot<String>()
            coVerify { emailRepo.sendHtmlEmail(any(), any(), capture(messageSlot)) }
            val message = messageSlot.captured
            assertTrue { (someNotificationResult.success + someNotificationResult2.success).toString() in message }
            assertTrue { (someNotificationResult.failure + someNotificationResult2.failure).toString() in message }
        }
    }
}