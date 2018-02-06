import io.mockk.*
import io.mockk.Ordering.*
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.usecases.addFeedback
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.data.News
import org.kotlinacademy.parseDate

class FeedbackTests {

    private val someFeedback = Feedback(1, 10, "Some comment", "Some suggestions")
    private val someEmail = "some@email.com"
    private val someNews = News(1, "News title", "News subtitle", "Image url", "Url", "2018-10-12T12:00:01".parseDate())

    @Test
    fun `addFeedback adds feedback to database once + addFeedback does not break when repo is not provided`() = runBlocking {
        val dbRepo = mockk<DatabaseRepository>(relaxed = true)
        addFeedback(someFeedback, null, dbRepo)
        coVerify(ordering = SEQUENCE) {
            dbRepo.addFeedback(someFeedback)
        }
    }

    @Test
    fun `addFeedback sends email after feedback is added`() = runBlocking {
        val dbRepo = mockk<DatabaseRepository>(relaxed = true)
        coEvery { dbRepo.getNews(any()) } returns someNews
        val emailRepo = mockk<EmailRepository>(relaxed = true)
        objectMockk(Config).use {
            every { Config.adminEmail } returns someEmail
            assert(Config.adminEmail != null)
            addFeedback(someFeedback, emailRepo, dbRepo)
            coVerify(ordering = ORDERED) {
                dbRepo.addFeedback(someFeedback)
                emailRepo.sendEmail(someEmail, any(), any())
            }
        }
    }
}