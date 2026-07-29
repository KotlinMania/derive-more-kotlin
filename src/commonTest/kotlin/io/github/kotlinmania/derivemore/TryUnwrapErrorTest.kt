// port-lint: tests tests/try_unwrap.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TryUnwrapErrorTest {
    @Test
    fun tryUnwrapErrorEqualityBasedOnAllFields() {
        val error1 = TryUnwrapError.new("input1", "Maybe", "Just", "tryUnwrapNothingMut")
        val error2 = TryUnwrapError.new("input1", "Maybe", "Just", "tryUnwrapNothingMut")
        val error3 = TryUnwrapError.new("input1", "Maybe", "Nothing", "tryUnwrapNothingMut")
        val error4 = TryUnwrapError.new("input1", "Either", "Just", "tryUnwrapNothingMut")
        val error5 = TryUnwrapError.new("input2", "Maybe", "Just", "tryUnwrapNothingMut")

        assertEquals(error1, error2)
        assertNotEquals(error1, error3)
        assertNotEquals(error1, error4)
        assertNotEquals(error1, error5)
        assertEquals(error1.hashCode(), error2.hashCode())
    }

    @Test
    fun tryUnwrapErrorDisplayWithDifferentVariantAndFunction() {
        val error = TryUnwrapError.new(
            input = "SomeValue",
            enumName = "Result",
            variantName = "Ok",
            funcName = "tryUnwrapErr",
        )
        assertEquals(
            "Attempt to call `Result::tryUnwrapErr()` on a `Result::Ok` value",
            error.toString(),
        )
    }

    @Test
    fun tryUnwrapErrorToExceptionPreservesMessage() {
        val error = TryUnwrapError.new("x", "Maybe", "Just", "unwrapNothing")
        val throwable = error.toException()
        assertEquals(error.toString(), throwable.message)
    }
}