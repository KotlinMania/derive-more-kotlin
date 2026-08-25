// port-lint: tests try_from.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ConvertTest {
    @Test
    fun tryFromReprErrorEqualityBasedOnInput() {
        val error1 = TryFromReprError.new(-1)
        val error2 = TryFromReprError.new(-1)
        val error3 = TryFromReprError.new(0)

        assertEquals(error1, error2)
        assertNotEquals(error1, error3)
        assertEquals(error1.hashCode(), error2.hashCode())
    }

    @Test
    fun tryIntoErrorEqualityBasedOnAllFields() {
        val error1 = TryIntoError.new("SmallInt", "SmallInt", "i32")
        val error2 = TryIntoError.new("SmallInt", "SmallInt", "i32")
        val error3 = TryIntoError.new("SmallInt", "BigInt", "i32")
        val error4 = TryIntoError.new("SmallInt", "SmallInt", "i64")

        assertEquals(error1, error2)
        assertNotEquals(error1, error3)
        assertNotEquals(error1, error4)
        assertEquals(error1.hashCode(), error2.hashCode())
    }

    @Test
    fun tryIntoErrorDisplayMessageWithMultipleVariantNames() {
        val error =
            TryIntoError.new(
                input = "SmallInt",
                variantNames = "NamedBigInt, UnsignedWithIgnoredField, NamedUnsignedWithIgnoredField",
                outputType = "Long",
            )
        assertEquals(
            "Only NamedBigInt, UnsignedWithIgnoredField, NamedUnsignedWithIgnoredField can be converted to Long",
            error.toString(),
        )
    }

    @Test
    fun tryIntoErrorDisplayMessageWithSingleVariantName() {
        val error =
            TryIntoError.new(
                input = "BigInt",
                variantNames = "SmallInt",
                outputType = "i32",
            )
        assertEquals("Only SmallInt can be converted to i32", error.toString())
    }

    @Test
    fun tryFromReprErrorDisplayWithNegativeInput() {
        val error = TryFromReprError.new(-1)
        assertEquals("`-1` does not correspond to a unit variant", error.toString())
    }

    @Test
    fun tryFromReprErrorDisplayWithZeroInput() {
        val error = TryFromReprError.new(0)
        assertEquals("`0` does not correspond to a unit variant", error.toString())
    }
}
