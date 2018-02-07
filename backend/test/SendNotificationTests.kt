import io.mockk.*
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository
import org.kotlinacademy.backend.usecases.sendNotification
import org.kotlinacademy.backend.usecases.sendNotifications
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SendNotificationTests {

    @Test
    fun `sendEmailWithInfoAboutFeedback sends notification with correct title, url, token and type`() = runBlocking {
        val body = "Some text"
        val url = "Some url"
        val token = "Some token"
        val type = FirebaseTokenType.Web

        val notificationRepo = mockk<NotificationsRepository>(relaxed = true)
        coEvery { notificationRepo.sendNotification(any(), any(), any(), any(), any()) } returns someNotificationResult

        // When
        val result = sendNotification(body, url, FirebaseTokenData(token, type), notificationRepo)

        // Then
        assertEquals(someNotificationResult, result)
        coVerify {
            notificationRepo.sendNotification("Kotlin Academy", body, any(), url, token)
        }
    }

    @Test
    fun `sendNotifications sends notification to all tokens in database`() = runBlocking {
        val body = "Some text"
        val url = "Some url"

        val dbRepo = mockk<DatabaseRepository>(relaxed = true)
        val notificationRepo = mockk<NotificationsRepository>(relaxed = true)
        coEvery { dbRepo.getAllTokens() } returns listOf(someFirebaseTokenData, someFirebaseTokenData2)
        coEvery { notificationRepo.sendNotification(any(), any(), any(), any(), any()) } returns someNotificationResult

        // When
        sendNotifications(body, url, dbRepo, notificationRepo)

        // Then
        coVerify(ordering = Ordering.SEQUENCE) {
            notificationRepo.sendNotification(any(), body, any(), url, someFirebaseTokenData.token)
            notificationRepo.sendNotification(any(), body, any(), url, someFirebaseTokenData2.token)
        }
    }

    @Test
    fun `sendNotifications sends email with summary `() = runBlocking {
        objectMockk(Config).use {
            every { Config.adminEmail } returns someEmail

            val dbRepo = mockk<DatabaseRepository>(relaxed = true)
            val notificationRepo = mockk<NotificationsRepository>(relaxed = true)
            val emailRepo = mockk<EmailRepository>(relaxed = true)
            coEvery { dbRepo.getAllTokens() } returns listOf(someFirebaseTokenData, someFirebaseTokenData2)
            coEvery { notificationRepo.sendNotification(any(), any(), any(), any(), any()) } returnsMany listOf(someNotificationResult, someNotificationResult2)

            // When
            sendNotifications("Some text", "Some url", dbRepo, notificationRepo, emailRepo)

            // Then
            val messageSlot = CapturingSlot<String>()
            coVerify { emailRepo.sendEmail(any(), any(), capture(messageSlot)) }
            val message = messageSlot.captured
            assertTrue { (someNotificationResult.success + someNotificationResult2.success).toString() in message }
            assertTrue { (someNotificationResult.failure + someNotificationResult2.failure).toString() in message }
        }
    }
}