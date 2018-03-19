import io.mockk.Ordering.ALL
import io.mockk.Ordering.ORDERED
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.usecases.MediumUseCase

class MediumTests : UseCaseTest() {

    @Test
    fun `syncWithMedium compares data from Medium and Database and updates if there are not contained news on Medium`() = runBlocking {
        // Given
        coEvery { mediumRepo.getNews() } returns listOf(someArticleData, someArticle2Data)
        coEvery { articlesDbRepo.getArticles() } returns listOf(someArticle2)

        // When
        MediumUseCase.sync()

        // Then
        coVerify(ordering = ALL) {
            mediumRepo.getNews()
            articlesDbRepo.getArticles()
            articlesDbRepo.addArticle(someArticleData)
        }
    }

    @Test
    fun `syncWithMedium does nothing if Medium returned no news`() = runBlocking {
        // Given
        coEvery { mediumRepo.getNews() } returns listOf()

        // When
        MediumUseCase.sync()

        // When
        coVerify(ordering = ORDERED) {
            mediumRepo.getNews()
        }
    }
}