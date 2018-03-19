import io.mockk.Ordering.ORDERED
import io.mockk.Ordering.SEQUENCE
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.usecases.FeedbackUseCese

class FeedbackTests : UseCaseTest() {

    @Test
    fun `addFeedback adds feedback to database once + addFeedback does not break when email repo is not provided`() = runBlocking {
        // Given
        EmailRepository.mock = null

        // When
        FeedbackUseCese.add(someFeedback)

        // Then
        coVerify(ordering = SEQUENCE) {
            feedbackDbRepo.addFeedback(someFeedback)
        }
    }

    @Test
    fun `addFeedback sends email after feedback is added`() = runBlocking {
        // Given
        Config.adminEmail = someEmail
        coEvery { articlesDbRepo.getArticle(any()) } returns someArticle

        // When
        FeedbackUseCese.add(someFeedback)

        // Then
        coVerify(ordering = ORDERED) {
            feedbackDbRepo.addFeedback(someFeedback)
            emailRepo.sendEmail(someEmail, any(), any())
        }
    }
}