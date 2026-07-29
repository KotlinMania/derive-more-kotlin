// port-lint: tests tests/add.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

class AddTest {
    @Test
    fun wrongVariantErrorCarriesOperationNameAndDisplayMessage() {
        val error = WrongVariantError.new("add")
        assertEquals("Trying to add() mismatched enum variants", error.toString())
        assertEquals("Trying to add() mismatched enum variants", error.message)
    }

    @Test
    fun binaryErrorMismatchWrapsWrongVariantError() {
        val wrongVariant = WrongVariantError.new("add")
        val error = BinaryError.Mismatch(wrongVariant)

        assertEquals(wrongVariant, error.error)
        assertEquals("Trying to add() mismatched enum variants", error.toString())
        assertEquals(wrongVariant, error.source())
    }

    @Test
    fun binaryErrorUnitWrapsUnitError() {
        val unitError = UnitError.new("add")
        val error = BinaryError.Unit(unitError)

        assertEquals(unitError, error.error)
        assertEquals("Cannot add() unit variants", error.toString())
        assertEquals(unitError, error.source())
    }
}
