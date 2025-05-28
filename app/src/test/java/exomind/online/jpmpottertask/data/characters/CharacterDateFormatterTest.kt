package exomind.online.jpmpottertask.data.characters

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.util.Locale

class CharacterDateFormatterTest {

    private lateinit var formatter: CharacterDateFormatter

    @Before
    fun setup() {
        Locale.setDefault(Locale.UK)
        formatter = CharacterDateFormatter()
    }

    @Test
    fun `format multiple cases`() {
        val cases = listOf<Pair<String?, String?>>(
            "12-12-2012" to "12 Dec 2012",
            "31-12-1999" to "31 Dec 1999",
            "29-02-2020" to "29 Feb 2020",
            "29-02-2019" to null,
            "" to null,
            "   " to null,
            null to null
        )

        cases.forEach { (rawDate, expected) ->
            val actual = formatter.format(rawDate = rawDate)
            assertEquals("Input: '$rawDate'", expected, actual)
        }
    }

    @Test
    fun `format returns null for null input`() {
        // WHEN
        val actual = formatter.format(rawDate = null)

        // THEN
        assertNull(actual)
    }

    @Test
    fun `format returns null for invalid date string`() {
        // GIVEN
        val raw = "invalid-date"

        // WHEN
        val actual = formatter.format(rawDate = raw)

        // THEN
        assertNull(actual)
    }
}
