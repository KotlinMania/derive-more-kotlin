// port-lint: source tests/try_from.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

class TryFromTest {
    @Test
    fun reprErrorCarriesInputAndDisplayMessage() {
        val error = TryFromReprError.new(-1)
        val throwable = error.toException()

        assertEquals(-1, error.input)
        assertEquals("`-1` does not correspond to a unit variant", throwable.message)
        assertEquals("`-1` does not correspond to a unit variant", error.toString())
    }
}
