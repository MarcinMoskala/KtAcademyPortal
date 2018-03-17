import io.mockk.Ordering.ALL
import io.mockk.Ordering.ORDERED
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.repositories.db.ArticlesDatabaseRepository
import org.kotlinacademy.backend.repositories.db.TokenDatabaseRepository
import org.kotlinacademy.backend.repositories.network.MediumRepository
import org.kotlinacademy.backend.repositories.network.NotificationsRepository
import org.kotlinacademy.backend.usecases.MediumUseCase

class MediumTests {

    @Test
    fun `syncWithMedium compares data from Medium and Database and updates if there are not contained news on Medium`() = runBlocking {
        val mediumRepo = mockk<MediumRepository>(relaxed = true)
        val artDbRepo = mockk<ArticlesDatabaseRepository>(relaxed = true)
        coEvery { mediumRepo.getNews() } returns listOf(someNews, someNews2)
        coEvery { artDbRepo.getArticles() } returns listOf(someNews2)

        // When
        MediumUseCase.sync(mediumRepo, artDbRepo)

        // Then
        coVerify(ordering = ALL) {
            mediumRepo.getNews()
            artDbRepo.getArticles()
            artDbRepo.addArticle(someNews, true)
        }
    }

    @Test
    fun `syncWithMedium does nothing if Medium returned no news`() = runBlocking {
        val mediumRepo = mockk<MediumRepository>(relaxed = true)
        val artDbRepo = mockk<ArticlesDatabaseRepository>(relaxed = true)
        coEvery { mediumRepo.getNews() } returns listOf()

        // When
        MediumUseCase.sync(mediumRepo, artDbRepo)

        // When
        coVerify(ordering = ORDERED) {
            mediumRepo.getNews()
        }
    }
}