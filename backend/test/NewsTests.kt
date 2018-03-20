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
    fun `getNewsData returns all articles, accepted infos and puzzlers`() = runBlocking {
        // Given
        coEvery { articlesDbRepo.getArticles() } returns listOf(someArticle, someArticle2)
        coEvery { infoDbRepo.getInfos() } returns listOf(someInfoAccepted, someInfoUnaccepted)
        coEvery { puzzlersDbRepo.getPuzzlers() } returns listOf(somePuzzlerUnaccepted, somePuzzlerAccepted)

        // When
        val ret = NewsUseCase.getNewsData()

        // Then
        assert(ret.articles == listOf(someArticle, someArticle2))
        assert(ret.infos == listOf(someInfoAccepted))
        assert(ret.puzzlers == listOf(somePuzzlerAccepted))
        coVerify(ordering = Ordering.UNORDERED) {
            articlesDbRepo.getArticles()
            infoDbRepo.getInfos()
            puzzlersDbRepo.getPuzzlers()
        }
    }

    @Test
    fun `When we propose info, it is added to database with acceptation false and email to admin`() = runBlocking {
        // Given
        coEvery { infoDbRepo.addInfo(someInfoData, false) } returns someInfoAccepted

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
        coEvery { puzzlersDbRepo.addPuzzler(somePuzzlerData, false) } returns somePuzzlerAccepted


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
        coEvery { infoDbRepo.getInfo(someInfoAccepted.id) } returns someInfoAccepted
        coEvery { tokenDbRepo.getAllTokens() } returns listOf(someFirebaseTokenData)
        coEvery { notificationsRepo.sendNotification(any(), any(), any(), any(), someFirebaseTokenData.token) } returns someNotificationResult

        // When
        NewsUseCase.acceptInfo(someInfoAccepted.id)

        // Then
        val infoSlot = slot<Info>()
        coVerify(ordering = Ordering.SEQUENCE) {
            infoDbRepo.getInfo(someInfoAccepted.id)
            infoDbRepo.updateInfo(capture(infoSlot))
            notificationsRepo.sendNotification(any(), any(), any(), any(), someFirebaseTokenData.token)
        }
        assert(infoSlot.captured.accepted)
    }

    @Test
    fun `When we accept puzzler, it is updated to acceptation true, and notifications to users are sent`() = runBlocking {
        // Given
        coEvery { puzzlersDbRepo.getPuzzler(somePuzzlerAccepted.id) } returns somePuzzlerAccepted
        coEvery { tokenDbRepo.getAllTokens() } returns listOf(someFirebaseTokenData)
        coEvery { notificationsRepo.sendNotification(any(), any(), any(), any(), someFirebaseTokenData.token) } returns someNotificationResult

        // When
        NewsUseCase.acceptPuzzler(somePuzzlerAccepted.id)

        // Then
        val puzzlerSlot = slot<Puzzler>()
        coVerify(ordering = Ordering.SEQUENCE) {
            puzzlersDbRepo.getPuzzler(somePuzzlerAccepted.id)
            puzzlersDbRepo.updatePuzzler(capture(puzzlerSlot))
            notificationsRepo.sendNotification(any(), any(), any(), any(), someFirebaseTokenData.token)
        }
        assert(puzzlerSlot.captured.accepted)
    }
}