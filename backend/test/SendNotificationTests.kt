import io.mockk.*
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.usecases.NotificationsUseCase
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SendNotificationTests : UseCaseTest() {

    @Test
    fun `sendEmailWithInfoAboutFeedback sends notification with correct title, url, token and type`() = runBlocking {
        val body = "Some text"
        val url = "Some url"
        val token = "Some token"
        val type = FirebaseTokenType.Web

        coEvery { notificationsRepo.sendNotification(any(), any(), any(), any(), any()) } returns someNotificationResult

        // When
        val result = NotificationsUseCase.send(body, url, FirebaseTokenData(token, type))

        // Then
        assertEquals(someNotificationResult, result)
        coVerify {
            notificationsRepo.sendNotification("Kotlin Academy", body, any(), url, token)
        }
    }

    @Test
    fun `sendNotifications sends notification to all tokens in database`() = runBlocking {
        val body = "Some text"
        val url = "Some url"

        coEvery { tokenDbRepo.getAllTokens() } returns listOf(someFirebaseTokenData, someFirebaseTokenData2)
        coEvery { notificationsRepo.sendNotification(any(), any(), any(), any(), any()) } returns someNotificationResult

        // When
        NotificationsUseCase.sendToAll(body, url)

        // Then
        coVerify(ordering = Ordering.SEQUENCE) {
            notificationsRepo.sendNotification(any(), body, any(), url, someFirebaseTokenData.token)
            notificationsRepo.sendNotification(any(), body, any(), url, someFirebaseTokenData2.token)
        }
    }

    @Test
    fun `sendNotifications sends email with summary `() = runBlocking {
        objectMockk(Config).use {
            every { Config.adminEmail } returns someEmail

            coEvery { tokenDbRepo.getAllTokens() } returns listOf(someFirebaseTokenData, someFirebaseTokenData2)
            coEvery { notificationsRepo.sendNotification(any(), any(), any(), any(), any()) } returnsMany listOf(someNotificationResult, someNotificationResult2)

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