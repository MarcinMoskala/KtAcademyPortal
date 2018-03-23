import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.usecases.NewsUseCase
import org.kotlinacademy.now

class ScheduleTests : UseCaseTest() {

    @Test
    fun `When we are after publish time, puzzler is published others are not`() = runBlocking {
        // Given
        coEvery { puzzlersDbRepo.addPuzzler(any(), false) } returns somePuzzlerAccepted
        coEvery { puzzlersDbRepo.getPuzzlers() } returns listOf()
        val schedule = mapOf(
                now.plusMinutes(1) to somePuzzlerData,
                now.plusMinutes(-1) to somePuzzlerData2
        )

        // When
        NewsUseCase.publishScheduled(schedule)

        // Then
        coVerify {
            puzzlersDbRepo.addPuzzler(somePuzzlerData2, false)
        }
        coVerify(inverse = true) {
            // This should not happen
            puzzlersDbRepo.addPuzzler(somePuzzlerData, false)
        }
    }

    @Test
    fun `We don't add puzzlers that are already on database`() = runBlocking {
        // Given
        coEvery { puzzlersDbRepo.addPuzzler(any(), false) } returns somePuzzlerAccepted
        coEvery { puzzlersDbRepo.getPuzzlers() } returns listOf(somePuzzlerUnaccepted)
        val schedule = mapOf(
                now.plusMinutes(-1) to somePuzzlerData,
                now.plusMinutes(-1) to somePuzzlerData2
        )

        // When
        NewsUseCase.publishScheduled(schedule)

        // Then
        coVerify {
            puzzlersDbRepo.addPuzzler(somePuzzlerData2, false)
        }
        coVerify(inverse = true) {
            // This should not happen
            puzzlersDbRepo.addPuzzler(somePuzzlerData, false)
        }
    }
}