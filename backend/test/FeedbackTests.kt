import io.mockk.*
import io.mockk.Ordering.ORDERED
import io.mockk.Ordering.SEQUENCE
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.db.ArticlesDatabaseRepository
import org.kotlinacademy.backend.repositories.db.FeedbackDatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.usecases.FeedbackUseCese

class
FeedbackTests {

    @Test
    fun `addFeedback adds feedback to database once + addFeedback does not break when repo is not provided`() = runBlocking {
        val feedbackDbRepo = mockk<FeedbackDatabaseRepository>(relaxed = true)
        val artDbRepo = mockk<ArticlesDatabaseRepository>(relaxed = true)

        // When
        FeedbackUseCese.add(someFeedback, null, artDbRepo, feedbackDbRepo)

        // Tten
        coVerify(ordering = SEQUENCE) {
            feedbackDbRepo.addFeedback(someFeedback)
        }
    }

    @Test
    fun `addFeedback sends email after feedback is added`() = runBlocking {
        objectMockk(Config).use {
            every { Config.adminEmail } returns someEmail
            val feedbackDbRepo = mockk<FeedbackDatabaseRepository>(relaxed = true)
            val artDbRepo = mockk<ArticlesDatabaseRepository>(relaxed = true)
            coEvery { artDbRepo.getArticle(any()) } returns someNews
            val emailRepo = mockk<EmailRepository>(relaxed = true)
            assert(Config.adminEmail != null)

            // When
            FeedbackUseCese.add(someFeedback, emailRepo, artDbRepo, feedbackDbRepo)

            // Then
            coVerify(ordering = ORDERED) {
                feedbackDbRepo.addFeedback(someFeedback)
                emailRepo.sendEmail(someEmail, any(), any())
            }
        }
    }
}