import io.mockk.mockk
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.db.*
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.repositories.network.medium.MediumRepository
import org.kotlinacademy.backend.repositories.network.notifications.NotificationsRepository
import kotlin.test.BeforeTest

abstract class UseCaseTest {

    protected lateinit var emailRepo: EmailRepository
    protected lateinit var mediumRepo: MediumRepository
    protected lateinit var notificationsRepo: NotificationsRepository
    protected lateinit var feedbackDbRepo: FeedbackDatabaseRepository
    protected lateinit var articlesDbRepo: ArticlesDatabaseRepository
    protected lateinit var infoDbRepo: InfoDatabaseRepository
    protected lateinit var puzzlersDbRepo: PuzzlersDatabaseRepository
    protected lateinit var snippetDbRepo: SnippetDatabaseRepository
    protected lateinit var tokenDbRepo: TokenDatabaseRepository

    @BeforeTest
    fun cleanUpMocks() {
        Config.adminEmail = adminEmail

        emailRepo = mockk(relaxed = true)
        EmailRepository.mock = emailRepo

        mediumRepo = mockk(relaxed = true)
        MediumRepository.mock = mediumRepo

        notificationsRepo = mockk(relaxed = true)
        NotificationsRepository.mock = notificationsRepo

        feedbackDbRepo = mockk(relaxed = true)
        FeedbackDatabaseRepository.mock = feedbackDbRepo

        articlesDbRepo = mockk(relaxed = true)
        ArticlesDatabaseRepository.mock = articlesDbRepo

        infoDbRepo = mockk(relaxed = true)
        InfoDatabaseRepository.mock = infoDbRepo

        puzzlersDbRepo = mockk(relaxed = true)
        PuzzlersDatabaseRepository.mock = puzzlersDbRepo

        snippetDbRepo = mockk(relaxed = true)
        SnippetDatabaseRepository.mock = snippetDbRepo

        tokenDbRepo = mockk(relaxed = true)
        TokenDatabaseRepository.mock = tokenDbRepo
    }
}