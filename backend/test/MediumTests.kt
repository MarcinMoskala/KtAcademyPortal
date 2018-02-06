import io.mockk.*
import io.mockk.Ordering.*
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.Config
import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.email.EmailRepository
import org.kotlinacademy.backend.repositories.network.MediumRepository
import org.kotlinacademy.backend.usecases.addFeedback
import org.kotlinacademy.backend.usecases.syncWithMedium
import org.kotlinacademy.data.Feedback
import org.kotlinacademy.data.News
import org.kotlinacademy.parseDate

class MediumTests {

    @Test
    fun `syncWithMedium compares data from Medium and Database and updates if there are not contained news on Medium`() = runBlocking {
        val mediumRepo = mockk<MediumRepository>(relaxed = true)
        val dbRepo = mockk<DatabaseRepository>(relaxed = true)
        coEvery { mediumRepo.getNews() } returns listOf(someNews, someNews2)
        coEvery { dbRepo.getNews() } returns listOf(someNews2)

        // When
        syncWithMedium(mediumRepo, dbRepo, null, null)

        // Then
        coVerify(ordering = ALL) {
            mediumRepo.getNews()
            dbRepo.getNews()
            dbRepo.addNews(someNews)
        }
    }

    @Test
    fun `syncWithMedium does nothing id Medium returned no news`() = runBlocking {
        val mediumRepo = mockk<MediumRepository>(relaxed = true)
        val dbRepo = mockk<DatabaseRepository>(relaxed = true)
        coEvery { mediumRepo.getNews() } returns listOf()

        // When
        syncWithMedium(mediumRepo, dbRepo, null, null)

        // When
        coVerify(ordering = ORDERED) {
            mediumRepo.getNews()
        }
    }
}