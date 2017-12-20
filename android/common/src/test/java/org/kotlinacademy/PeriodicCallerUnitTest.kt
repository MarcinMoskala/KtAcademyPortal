@file:Suppress("IllegalIdentifier")

package org.kotlinacademy

import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.kotlinacademy.common.delay
import org.kotlinacademy.usecases.PeriodicCaller
import kotlin.test.assertTrue

class PeriodicCallerUnitTest {

    @Test
    fun `Periodic caller for 50ms is called around 20 times in 1 second`() {
        val caller = PeriodicCaller.PeriodicCallerImpl()
        var count = 0
        runBlocking {
            val job = caller.start(50) { count++ }
            delay(1000)
            job.cancel()
        }
        assertTrue (count in 17..23)
    }
}
