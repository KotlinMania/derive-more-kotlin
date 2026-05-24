// port-lint: source tests/try_unwrap.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

class TryUnwrapTest {
    @Test
    fun tryUnwrapErrorCarriesInputAndDisplayMessage() {
        val input = "Maybe.Nothing"
        val error = TryUnwrapError.new(
            input = input,
            enumName = "Maybe",
            variantName = "Just",
            funcName = "tryUnwrapNothingMut",
        )
        val throwable = error.toException()

        assertEquals(input, error.input)
        assertEquals(
            "Attempt to call `Maybe::tryUnwrapNothingMut()` on a `Maybe::Just` value",
            throwable.message,
        )
        assertEquals(
            "Attempt to call `Maybe::tryUnwrapNothingMut()` on a `Maybe::Just` value",
            error.toString(),
        )
    }
}
