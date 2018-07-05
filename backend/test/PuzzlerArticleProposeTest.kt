import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.usecases.MediumUseCase
import kotlin.test.assertEquals

class PuzzlerArticleProposeTest : UseCaseTest() {

    @Test
    fun `Do not publishes for no puzzlers`() = runBlocking {
        // Given
        coEvery { puzzlersDbRepo.getPuzzlers() } returns listOf()

        // When
        MediumUseCase.proposePostWithLastWeekPuzzlers()

        // Then
        coVerify(inverse = true) {
            mediumRepo.postPost(any(), any(), any())
        }
    }

    @Test
    fun `Do not publishes for single puzzlers`() = runBlocking {
        // Given
        coEvery { puzzlersDbRepo.getPuzzlers() } returns listOf(somePuzzlerAccepted)

        // When
        MediumUseCase.proposePostWithLastWeekPuzzlers()

        // Then
        coVerify(inverse = true) {
            mediumRepo.postPost(any(), any(), any())
        }
    }

    @Test
    fun `Publishes for two puzzlers`() = runBlocking {
        // Given
        coEvery { puzzlersDbRepo.getPuzzlers() } returns listOf(somePuzzlerAccepted, somePuzzlerAccepted2)
        coEvery { mediumRepo.getPosts() } returns listOf()

        // When
        MediumUseCase.proposePostWithLastWeekPuzzlers()

        // Then
        coVerify {
            mediumRepo.postPost(any(), any(), any())
        }
    }

    @Test
    fun `Next title gives title with next number then ones that are already published`() = runBlocking {
        fun constructTitle(num: Int) = "Puzzlers on Kotlin Academy, week $num"
        val a1 = someArticleData.copy(title = constructTitle(1))
        val a2 = someArticleData.copy(title = constructTitle(2))
        val a3 = someArticleData.copy(title = constructTitle(3))
        val a307 = someArticleData.copy(title = constructTitle(307))

        assertEquals(constructTitle(4), MediumUseCase.nextWeeklyPuzzlersPostTitle(listOf(a1,a2,a3)))
        assertEquals(constructTitle(4), MediumUseCase.nextWeeklyPuzzlersPostTitle(listOf(a1,a3)))
        assertEquals(constructTitle(4), MediumUseCase.nextWeeklyPuzzlersPostTitle(listOf(a3)))
        assertEquals(constructTitle(2), MediumUseCase.nextWeeklyPuzzlersPostTitle(listOf(a1)))

        assertEquals(constructTitle(308), MediumUseCase.nextWeeklyPuzzlersPostTitle(listOf(a1, a2, a3, a307)))

        assertEquals(constructTitle(1), MediumUseCase.nextWeeklyPuzzlersPostTitle(emptyList()))
    }
}