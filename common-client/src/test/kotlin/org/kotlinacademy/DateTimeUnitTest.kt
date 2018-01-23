package org.kotlinacademy

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DateTimeUnitTest {

    // Two way conversion should give the same result
    @Test
    fun twoSideConversionTest() {
        val dateFormatted = "2018-10-12T12:00:01"
        assertEquals(dateFormatted, dateFormatted.parseDate().toDateFormatString())
    }

    // Ordering is correct after parse
    @Test
    fun datetimeParsingTest() {
        val date1 = "2018-10-12T12:00:01".parseDate()
        val date2 = "2018-10-12T12:10:01".parseDate()
        val date3 = "2018-10-12T13:00:01".parseDate()
        assertTrue(date1 < date2)
        assertTrue(date2 < date3)
        assertTrue(date1 < date3)
    }
}