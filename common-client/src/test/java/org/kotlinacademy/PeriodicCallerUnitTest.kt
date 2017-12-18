@file:Suppress("IllegalIdentifier")

package org.kotlinacademy

import kotlinx.coroutines.experimental.runBlocking
import org.kotlinacademy.common.delay
import org.kotlinacademy.usecases.PeriodicCaller
import kotlin.test.assertEquals

class PeriodicCallerUnitTest {

    @Test
    fun `When started, `() {
        val caller = PeriodicCaller.PeriodicCallerImpl()
        var count = 0
        runBlocking {
            val job = caller.start(50) { count++ }
            delay(1000)
            job.cancel()
        }
        assertEquals(20, count)
        assert(count in 15..25)
    }
}
