import io.mockk.*
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.usecases.sendEmailWithInfoAboutFeedback
import org.kotlinacademy.backend.usecases.sendEmailWithNotificationResult
import kotlin.test.assertTrue

class SendEmailTests {

    @Test
    fun `sendEmailWithInfoAboutFeedback sends email that includes comment, suggestions and rating`() = runBlocking {
        objectMockk(Config).use {
            val message = CapturingSlot<String>()
            every { Config.adminEmail } returns someEmail
            val emailRepo = mockk<EmailRepository>(relaxed = true)
            val dbRepo = mockk<DatabaseRepository>(relaxed = true)
            coEvery { dbRepo.getNews(any()) } returns someNews
            coEvery { emailRepo.sendEmail(any(), any(), capture(message)) } just runs

            // When
            sendEmailWithInfoAboutFeedback(someFeedback, emailRepo, dbRepo)

            // Then
            coVerify {
                dbRepo.getNews(someFeedback.newsId!!)
                emailRepo.sendEmail(someEmail, any(), any())
            }

            val messageText = message.captured
            assertTrue { someFeedback.comment in messageText }
            assertTrue { someFeedback.suggestions in messageText }
            assertTrue { someFeedback.rating.toString() in messageText }
        }
    }

    @Test
    fun `sendEmailWithNotificationResult sends email that includes notification results`() = runBlocking {
        objectMockk(Config).use {
            val message = CapturingSlot<String>()
            every { Config.adminEmail } returns someEmail
            val emailRepo = mockk<EmailRepository>(relaxed = true)
            coEvery { emailRepo.sendEmail(any(), any(), capture(message)) } just runs

            // When
            sendEmailWithNotificationResult(someNotificationResult, emailRepo)

            // Then
            coVerify {
                emailRepo.sendEmail(someEmail, any(), any())
            }

            val messageText = message.captured
            assertTrue { someNotificationResult.failure.toString() in messageText }
            assertTrue { someNotificationResult.success.toString() in messageText }
        }
    }
}