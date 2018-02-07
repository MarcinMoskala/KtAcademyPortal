import io.mockk.Ordering.ALL
import io.mockk.Ordering.ORDERED
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.backend.repositories.db.DatabaseRepository
import org.kotlinacademy.backend.repositories.network.MediumRepository
import org.kotlinacademy.backend.usecases.syncWithMedium

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