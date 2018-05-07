import io.mockk.*
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.usecases.JobsUseCase
import org.kotlinacademy.backend.usecases.NewsUseCase
import org.kotlinacademy.data.Article
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.Puzzler
import org.kotlinacademy.now

class FillDayWithPuzzlerTests : UseCaseTest() {

    private val waitingPuzzler = somePuzzlerUnaccepted.copy(dateTime = now.minusDays(10))
    private val todayPuzzler = somePuzzlerAccepted2.copy(dateTime = now)
    private val yesterdayPuzzler = somePuzzlerAccepted2.copy(dateTime = now.minusDays(1))
    private val todayInfo = someInfoAccepted.copy(dateTime = now)
    private val yesterdayInfo = someInfoAccepted.copy(dateTime = now.minusDays(1))
    private val todayArticle = someArticle.copy(dateTime = now)
    private val yesterdayArticle = someArticle.copy(dateTime = now.minusDays(1))

    @Test
    fun `If article was published in this day, nothing happens`() = runBlocking {
        checkNothingHappensFor(
                articles = listOf(todayArticle),
                infos = listOf(),
                puzzlers = listOf(waitingPuzzler)
        )
    }

    @Test
    fun `If info was published in this day, nothing happens`() = runBlocking {
        checkNothingHappensFor(
                articles = listOf(),
                infos = listOf(todayInfo),
                puzzlers = listOf(waitingPuzzler)
        )
    }

    @Test
    fun `If puzzler was published in this day, nothing happens`() = runBlocking {
        checkNothingHappensFor(
                articles = listOf(),
                infos = listOf(),
                puzzlers = listOf(todayPuzzler, waitingPuzzler)
        )
    }

    @Test
    fun `When there are no waiting puzzlers, nothing happens`() = runBlocking {
        checkNothingHappensFor(
                articles = listOf(),
                infos = listOf(),
                puzzlers = listOf(yesterdayPuzzler)
        )
    }

    @Test
    fun `When no puzzler or info from today and there is at least single waiting puzzler it is published`() = runBlocking {
        checkWaitingPuzzlerAccepted(waitingPuzzler,
                articles = listOf(yesterdayArticle),
                infos = listOf(yesterdayInfo),
                puzzlers = listOf(yesterdayPuzzler)
        )
    }

    @Test
    fun `Oldest puzzler is accepted`() = runBlocking {
        val yesterdayWaitingPuzzler = waitingPuzzler.copy(id = 123, dateTime = now.minusDays(1))
        val lastWeekWaitingPuzzler = waitingPuzzler.copy(id = 345, dateTime = now.minusDays(7))

        checkWaitingPuzzlerAccepted(lastWeekWaitingPuzzler,
                articles = listOf(),
                infos = listOf(),
                puzzlers = listOf(lastWeekWaitingPuzzler)
        )
    }

    private suspend fun checkNothingHappensFor(articles: List<Article>, infos: List<Info>, puzzlers: List<Puzzler>) {
        coEvery { articlesDbRepo.getArticles() } returns articles
        coEvery { infoDbRepo.getInfos() } returns infos
        coEvery { puzzlersDbRepo.getPuzzlers() } returns puzzlers

        JobsUseCase.fillDayWithPuzzler()

        coVerify(inverse = true) {
            puzzlersDbRepo.updatePuzzler(any())
        }
    }

    private suspend fun checkWaitingPuzzlerAccepted(waitingPuzzler: Puzzler, articles: List<Article>, infos: List<Info>, puzzlers: List<Puzzler>) {
        objectMockk(NewsUseCase).use {
            coEvery { NewsUseCase.acceptPuzzler(any()) } just runs
            coEvery { articlesDbRepo.getArticles() } returns articles
            coEvery { infoDbRepo.getInfos() } returns infos
            coEvery { puzzlersDbRepo.getPuzzlers() } returns puzzlers + waitingPuzzler

            JobsUseCase.fillDayWithPuzzler()

            coVerify {
                NewsUseCase.acceptPuzzler(waitingPuzzler.id)
            }
        }
    }
}