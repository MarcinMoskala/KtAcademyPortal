import io.mockk.*
import io.mockk.Ordering.ORDERED
import io.mockk.Ordering.SEQUENCE
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.usecases.addFeedback

class
FeedbackTests {

    @Test
    fun `addFeedback adds feedback to database once + addFeedback does not break when repo is not provided`() = runBlocking {
        val dbRepo = mockk<DatabaseRepository>(relaxed = true)

        // When
        addFeedback(someFeedback, null, dbRepo)

        // Tten
        coVerify(ordering = SEQUENCE) {
            dbRepo.addFeedback(someFeedback)
        }
    }

    @Test
    fun `addFeedback sends email after feedback is added`() = runBlocking {
        objectMockk(Config).use {
            every { Config.adminEmail } returns someEmail
            val dbRepo = mockk<DatabaseRepository>(relaxed = true)
            coEvery { dbRepo.getNews(any()) } returns someNews
            val emailRepo = mockk<EmailRepository>(relaxed = true)
            assert(Config.adminEmail != null)

            // When
            addFeedback(someFeedback, emailRepo, dbRepo)

            // Then
            coVerify(ordering = ORDERED) {
                dbRepo.addFeedback(someFeedback)
                emailRepo.sendEmail(someEmail, any(), any())
            }
        }
    }
}