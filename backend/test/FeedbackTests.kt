import com.sun.xml.internal.bind.v2.TODO
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.usecases.addFeedback
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType
import org.kotlinacademy.data.News

class FeedbackTests {

    private val someFeedback = Feedback(1,10, "Some comment", "Some suggestions")

    @Test
    fun `addFeedback adds feedback to database`() = runBlocking {
        val dbRepo = mockk<EmailRepository>()
//        addFeedback(someFeedback, null, dbRepo)
//        verify {
//            dbRepo.addFeedback(someFeedback)
//        }
    }
}