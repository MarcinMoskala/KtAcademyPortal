import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.usecases.EmailUseCase
import kotlin.test.assertTrue

class SendEmailTests : UseCaseTest() {

    @Test
    fun `sendEmailWithInfoAboutFeedback sends email that includes comment, suggestions and rating`() = runBlocking {
        // Given
        coEvery { articlesDbRepo.getArticle(any()) } returns someArticle

        // When
        EmailUseCase.sendInfoAboutFeedback(someFeedback)

        // Then
        val message = CapturingSlot<String>()
        coVerify {
            articlesDbRepo.getArticle(someFeedback.newsId!!)
            emailRepo.sendHtmlEmail(adminEmail, any(), capture(message))
        }
        val messageText = message.captured
        assertTrue { someFeedback.comment in messageText }
        assertTrue { someFeedback.suggestions in messageText }
        assertTrue { someFeedback.rating.toString() in messageText }
    }

    @Test
    fun `sendEmailWithNotificationResult sends email that includes notification results`() = runBlocking {
        // When
        EmailUseCase.sendNotificationResult(someNotificationResult)

        // Then
        val message = CapturingSlot<String>()
        coVerify {
            emailRepo.sendHtmlEmail(adminEmail, any(), capture(message))
        }
        val messageText = message.captured
        assertTrue { someNotificationResult.failure.toString() in messageText }
        assertTrue { someNotificationResult.success.toString() in messageText }
    }
}