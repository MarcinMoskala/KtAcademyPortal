import io.mockk.*
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.repositories.network.notifications.NotificationResult
import org.kotlinacademy.backend.usecases.MediumUseCase
import org.kotlinacademy.backend.usecases.PromotionUseCase

class PromotionPropositionTests : UseCaseTest() {

    @Test
    fun `Does not happend when articles are the same`() = runBlocking {
        objectMockk(PromotionUseCase).use {
            // Given
            every { PromotionUseCase.proposePromotion(any()) } just runs
            coEvery { mediumRepo.getPosts() } returns listOf(someArticleData, someArticleData2)
            coEvery { articlesDbRepo.getArticles() } returns listOf(someArticle, someArticle2)

            // When
            MediumUseCase.sync()

            // Then
            verify(inverse = true) { PromotionUseCase.proposePromotion(any()) }
        }
    }

    @Test
    fun `Proposition happends when there is one new article`() = runBlocking {
        objectMockk(PromotionUseCase).use {
            // Given
            every { PromotionUseCase.proposePromotion(any()) } just runs
            coEvery { notificationsRepo.sendNotification(any(), any()) } returns NotificationResult(1, 0)
            coEvery { tokenDbRepo.getAllTokens() } returns listOf(someFirebaseTokenData)
            coEvery { mediumRepo.getPosts() } returns listOf(someArticleData, someArticleData2)
            coEvery { articlesDbRepo.getArticles() } returns listOf(someArticle)

            // When
            MediumUseCase.sync()

            // Then
            verify { PromotionUseCase.proposePromotion(any()) }
        }
    }

    @Test
    fun `Proposition happends multiple times when there are multiple new articles`() = runBlocking {
        objectMockk(PromotionUseCase).use {
            // Given
            every { PromotionUseCase.proposePromotion(any()) } just runs
            coEvery { notificationsRepo.sendNotification(any(), any()) } returns NotificationResult(1, 0)
            coEvery { tokenDbRepo.getAllTokens() } returns listOf(someFirebaseTokenData)
            coEvery { mediumRepo.getPosts() } returns listOf(someArticleData, someArticleData2)
            coEvery { articlesDbRepo.getArticles() } returns listOf()

            // When
            MediumUseCase.sync()

            // Then
            verify {
                PromotionUseCase.proposePromotion(any())
                PromotionUseCase.proposePromotion(any())
            }
        }
    }
}