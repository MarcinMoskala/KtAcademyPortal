import io.mockk.*
import io.mockk.Ordering.SEQUENCE
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository
import org.kotlinacademy.backend.repositories.network.dto.NotificationResult
import org.kotlinacademy.backend.usecases.addNews
import org.kotlinacademy.backend.usecases.addOrUpdateNews
import org.kotlinacademy.backend.usecases.deleteNews
import org.kotlinacademy.backend.usecases.getAllNews
import org.kotlinacademy.data.FirebaseTokenData
import org.kotlinacademy.data.FirebaseTokenType
import org.kotlinacademy.data.News
import kotlin.test.assertEquals

class NewsTests {

    @Test
    fun `addOrUpdateNews adds news if id is null`() = runBlocking {
        val dbRepo = mockk<DatabaseRepository>(relaxed = true)

        // When
        addOrUpdateNews(newNews, dbRepo, null, null)

        // Then
        coVerify(ordering = SEQUENCE) {
            dbRepo.addNews(newNews)
        }
    }

    @Test
    fun `addOrUpdateNews update news if id is null`() = runBlocking {
        val dbRepo = mockk<DatabaseRepository>(relaxed = true)

        // When
        addOrUpdateNews(someNews, dbRepo, null, null)

        // Then
        coVerify(ordering = SEQUENCE) {
            dbRepo.updateNews(someNews.id!!, someNews)
        }
    }

    @Test
    fun `addOrUpdateNews sends notification and email when new news was added`() = runBlocking {
        // TODO Use function reference as extension when it will be supported for suspending function
        `function sends notification and email when new news was added` { news, dbRepo, notificationsRepo, emailRepo ->
            addOrUpdateNews(news, dbRepo, notificationsRepo, emailRepo)
        }
    }

    @Test
    fun `addNews sends notification and email when new news was added`() = runBlocking {
        // TODO Use function reference as extension when it will be supported for suspending function
        `function sends notification and email when new news was added` { news, dbRepo, notificationsRepo, emailRepo ->
            addNews(news, dbRepo, notificationsRepo, emailRepo)
        }
    }

    @Test
    fun `addNews sends notification with url to KotlinAcademy Blog if news url is empty`() = runBlocking {
        val dbRepo = mockk<DatabaseRepository>(relaxed = true)
        coEvery { dbRepo.getAllTokens() } returns listOf(FirebaseTokenData("", FirebaseTokenType.Android))
        val notificationsRepo = mockk<NotificationsRepository>(relaxed = true)
        coEvery { notificationsRepo.sendNotification(any(), any(), any(), any(), any()) } returns NotificationResult(1, 0)

        // When
        addNews(newNews.copy(url = null), dbRepo, notificationsRepo, null)

        // Then
        coVerify {
            notificationsRepo.sendNotification(any(), any(), any(), "https://blog.kotlin-academy.com/", any())
        }
    }

    @Test
    fun `deleteNews delete news from database`() = runBlocking {
        val id = 1
        val dbRepo = mockk<DatabaseRepository>(relaxed = true)

        // When
        deleteNews(id, dbRepo)

        // Then
        coVerify {
            dbRepo.deleteNews(id)
        }
    }

    @Test
    fun `getAllNews delete news from database`() = runBlocking {
        val allNews = listOf(someNews, someNews2)
        val dbRepo = mockk<DatabaseRepository>(relaxed = true)
        coEvery { dbRepo.getNews() } returns allNews

        // When
        val news = getAllNews(dbRepo)

        // Then
        assertEquals(allNews, news)
    }

    private fun `function sends notification and email when new news was added`(function: suspend (News, DatabaseRepository, NotificationsRepository?, EmailRepository?) -> Unit) = runBlocking {
        objectMockk(Config).use {
            every { Config.adminEmail } returns someEmail
            val dbRepo = mockk<DatabaseRepository>(relaxed = true)
            coEvery { dbRepo.getAllTokens() } returns listOf(FirebaseTokenData("", FirebaseTokenType.Android))
            val emailRepo = mockk<EmailRepository>(relaxed = true)
            val notificationsRepo = mockk<NotificationsRepository>(relaxed = true)
            coEvery { notificationsRepo.sendNotification(any(), any(), any(), any(), any()) } returns NotificationResult(1, 0)

            // When
            function(newNews, dbRepo, notificationsRepo, emailRepo)

            // Then
            coVerify(ordering = SEQUENCE) {
                dbRepo.addNews(newNews)
                dbRepo.getAllTokens()
                notificationsRepo.sendNotification(any(), any(), any(), any(), any())
                emailRepo.sendEmail(any(), any(), any())
            }
        }
    }
}