// port-lint: source tests/try_into.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

class TryIntoTest {
    @Test
    fun conversionErrorCarriesInputAndDisplayMessage() {
        val error = TryIntoError.new(
            input = "SmallInt",
            variantNames = "NamedBigInt, UnsignedWithIgnoredField, NamedUnsignedWithIgnoredField",
            outputType = "Long",
        )
        val throwable = error.toException()

        assertEquals("SmallInt", error.input)
        assertEquals(
            "Only NamedBigInt, UnsignedWithIgnoredField, NamedUnsignedWithIgnoredField can be converted to Long",
            throwable.message,
        )
        assertEquals(
            "Only NamedBigInt, UnsignedWithIgnoredField, NamedUnsignedWithIgnoredField can be converted to Long",
            error.toString(),
        )
    }
}
