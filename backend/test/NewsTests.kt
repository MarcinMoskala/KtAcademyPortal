import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.slot
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.usecases.NewsUseCase
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.Puzzler

class NewsTests : UseCaseTest() {

    @Test
    fun `When we propose info, it is added to database with acceptation false and email to admin`() = runBlocking {
        // Given
        coEvery { infoDbRepo.addInfo(someInfoData, false) } returns someInfo

        // When
        NewsUseCase.propose(someInfoData)

        // Then
        coVerify(ordering = Ordering.SEQUENCE) {
            infoDbRepo.addInfo(someInfoData, false)
            emailRepo.sendEmail(adminEmail, any(), any())
        }
    }

    @Test
    fun `When we propose info, saved info has now as dateTime`() = runBlocking {
        // TODO
    }

    @Test
    fun `When we propose puzzler, it is added to database with acceptation false and email to admin`() = runBlocking {
        // Given
        coEvery { puzzlersDbRepo.addPuzzler(somePuzzlerData, false) } returns somePuzzler


        // When
        NewsUseCase.propose(somePuzzlerData)

        // Then
        coVerify(ordering = Ordering.SEQUENCE) {
            puzzlersDbRepo.addPuzzler(somePuzzlerData, false)
            emailRepo.sendEmail(adminEmail, any(), any())
        }
    }

    @Test
    fun `When we accept info, it is updated to acceptation true, and notifications to users are sent`() = runBlocking {
        // Given
        coEvery { infoDbRepo.getInfo(someInfo.id) } returns someInfo
        coEvery { tokenDbRepo.getAllTokens() } returns listOf(someFirebaseTokenData)
        coEvery { notificationsRepo.sendNotification(any(), any(), any(), any(), someFirebaseTokenData.token) } returns someNotificationResult

        // When
        NewsUseCase.acceptInfo(someInfo.id)

        // Then
        val infoSlot = slot<Info>()
        coVerify(ordering = Ordering.SEQUENCE) {
            infoDbRepo.getInfo(someInfo.id)
            infoDbRepo.updateInfo(capture(infoSlot))
            notificationsRepo.sendNotification(any(), any(), any(), any(), someFirebaseTokenData.token)
        }
        assert(infoSlot.captured.accepted)
    }

    @Test
    fun `When we accept puzzler, it is updated to acceptation true, and notifications to users are sent`() = runBlocking {
        // Given
        coEvery { puzzlersDbRepo.getPuzzler(somePuzzler.id) } returns somePuzzler
        coEvery { tokenDbRepo.getAllTokens() } returns listOf(someFirebaseTokenData)
        coEvery { notificationsRepo.sendNotification(any(), any(), any(), any(), someFirebaseTokenData.token) } returns someNotificationResult

        // When
        NewsUseCase.acceptPuzzler(somePuzzler.id)

        // Then
        val puzzlerSlot = slot<Puzzler>()
        coVerify(ordering = Ordering.SEQUENCE) {
            puzzlersDbRepo.getPuzzler(somePuzzler.id)
            puzzlersDbRepo.updatePuzzler(capture(puzzlerSlot))
            notificationsRepo.sendNotification(any(), any(), any(), any(), someFirebaseTokenData.token)
        }
        assert(puzzlerSlot.captured.accepted)
    }
}