import io.mockk.Ordering.ALL
import io.mockk.Ordering.ORDERED
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.repositories.network.notifications.NotificationResult
import org.kotlinacademy.backend.usecases.MediumUseCase

class MediumTests : UseCaseTest() {

    @Test
    fun `syncWithMedium compares data from Medium and Database and updates if there are not contained news on Medium`() = runBlocking {
        // Given
        coEvery { notificationsRepo.sendNotification(any(), any()) } returns NotificationResult(1, 0)
        coEvery { tokenDbRepo.getAllTokens() } returns listOf(someFirebaseTokenData)
        coEvery { mediumRepo.getPosts() } returns listOf(someArticleData, someArticleData2)
        coEvery { articlesDbRepo.getArticles() } returns listOf(someArticle2)

        // When
        MediumUseCase.sync()

        // Then
        coVerify(ordering = ALL) {
            mediumRepo.getPosts()
            articlesDbRepo.getArticles()
            articlesDbRepo.addArticle(someArticleData)
            notificationsRepo.sendNotification(any(), any())
        }
    }

    @Test
    fun `syncWithMedium does nothing if Medium returned no news`() = runBlocking {
        // Given
        coEvery { mediumRepo.getPosts() } returns listOf()

        // When
        MediumUseCase.sync()

        // When
        coVerify(ordering = ORDERED) {
            mediumRepo.getPosts()
        }
        coVerify(inverse = true) {
            articlesDbRepo.addArticle(any())
            notificationsRepo.sendNotification(any(), any())
        }
    }



    @Test
    fun `syncWithMedium does not include weekly articles with puzzlers`() = runBlocking {
        // Given
        coEvery { mediumRepo.getPosts() } returns listOf(someWeeklyPuzzlersArticleData, someWeeklyPuzzlersArticleData2)

        // When
        MediumUseCase.sync()

        // When
        coVerify(ordering = ORDERED) {
            mediumRepo.getPosts()
        }
        coVerify(inverse = true) {
            articlesDbRepo.addArticle(any())
            notificationsRepo.sendNotification(any(), any())
        }
    }


}