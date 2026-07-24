// port-lint: tests tests/from_str.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class StrTest {
    @Test
    fun fromStrErrorCarriesTypeNameAndDisplayMessage() {
        val error = FromStrError.new("MyEnum")
        assertEquals("Invalid `MyEnum` string representation", error.toString())
        assertEquals("Invalid `MyEnum` string representation", error.message)
    }

    @Test
    fun fromStrErrorEqualityBasedOnTypeName() {
        val error1 = FromStrError.new("Color")
        val error2 = FromStrError.new("Color")
        val error3 = FromStrError.new("Shape")

        assertEquals(error1, error2)
        assertNotEquals(error1, error3)
        assertEquals(error1.hashCode(), error2.hashCode())
        assertNotEquals(error1.hashCode(), error3.hashCode())
    }

    @Test
    fun fromStrErrorMessageIsAccessibleViaException() {
        val error = FromStrError.new("MyEnum")
        assertEquals("Invalid `MyEnum` string representation", error.message)
    }
}