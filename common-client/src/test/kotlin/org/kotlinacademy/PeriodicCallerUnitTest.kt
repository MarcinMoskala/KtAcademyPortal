package org.kotlinacademy

import org.kotlinacademy.common.delay
import org.kotlinacademy.usecases.PeriodicCaller
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertTrue

class PeriodicCallerUnitTest {

    // Problems with current execution on JS
    @Ignore
    @Test
    @JsName("numberOfCallsInTimeTest")
    fun `Periodic caller for 50ms is called around 20 times in 1 second`() {
        val caller = PeriodicCaller.PeriodicCallerImpl()
        var count = 0
        runBlocking {
            val job = caller.start(50) { count++ }
            delay(1000)
            job.cancel()
        }
        assertTrue(count in 17..23)
//        assertTrue { false }
    }
}
