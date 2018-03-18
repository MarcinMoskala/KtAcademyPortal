import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.usecases.NewsUseCase

class NewsTests : UseCaseTest() {

    @Test
    fun `When we propose info, it is added to database with acceptation false and email to admin`() = runBlocking {
        // When
        NewsUseCase.propose(someInfo)

        // Then
        coVerify(ordering = Ordering.SEQUENCE) {
            infoDbRepo.addInfo(someInfo, false)
            emailRepo.sendEmail(adminEmail, any(), any())
        }
    }

    @Test
    fun `When we propose puzzler, it is added to database with acceptation false and email to admin`() = runBlocking {
        // When
        NewsUseCase.propose(somePuzzler)

        // Then
        coVerify(ordering = Ordering.SEQUENCE) {
            puzzlersDbRepo.addPuzzler(somePuzzler, false)
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
        coVerify(ordering = Ordering.SEQUENCE) {
            infoDbRepo.getInfo(someInfo.id)
            infoDbRepo.updateInfo(someInfo.id, any(), true)
            notificationsRepo.sendNotification(any(), any(), any(), any(), someFirebaseTokenData.token)
        }
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
        coVerify(ordering = Ordering.SEQUENCE) {
            puzzlersDbRepo.getPuzzler(somePuzzler.id)
            puzzlersDbRepo.updatePuzzler(somePuzzler.id, any(), true)
            notificationsRepo.sendNotification(any(), any(), any(), any(), someFirebaseTokenData.token)
        }
    }
}