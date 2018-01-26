package org.kotlinacademy

import org.kotlinacademy.common.delay
import org.kotlinacademy.usecases.PeriodicCaller
import kotlin.test.Test
import kotlin.test.assertTrue

class PeriodicCallerUnitTest: BaseUnitTest() {

    @Test
    @JsName("numberOfCallsInTimeTest")
    fun `Periodic caller for 50ms is called around 20 times during 1 second`() = runBlocking {
        val caller = PeriodicCaller.PeriodicCallerImpl()
        var count = 0
        val job = caller.start(50) { count++ }
        delay(1000)
        job.cancel()
        assertTrue(count in 17..23)
    }
}
