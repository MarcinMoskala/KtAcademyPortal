import io.mockk.*
import io.mockk.Ordering.SEQUENCE
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.repositories.db.ArticlesDatabaseRepository
import org.kotlinacademy.backend.repositories.db.FeedbackDatabaseRepository
import org.kotlinacademy.backend.usecases.*
import kotlin.test.assertEquals

class NewsTests {

    @Test
    fun `addOrUpdateNews adds news if id is null`() = runBlocking {
        val dbRepo = mockk<ArticlesDatabaseRepository>(relaxed = true)
        coEvery { dbRepo.addArticle(any(), any()) } just runs

        // When
        NewsUseCase.addOrUpdate(newNews, dbRepo, null)

        // Then
        coVerify(ordering = SEQUENCE) {
            dbRepo.addArticle(newNews, false)
        }
    }

    @Test
    fun `addOrUpdateNews update news if id is not null`() = runBlocking {
        val dbRepo = mockk<ArticlesDatabaseRepository>(relaxed = true)
        coEvery { dbRepo.updateArticle(any(), any()) } just runs

        // When
        NewsUseCase.addOrUpdate(someNews, dbRepo, null)

        // Then
        coVerify(ordering = SEQUENCE) {
            dbRepo.updateArticle(someNews.id, someNews)
        }
    }
}