// port-lint: tests boats_display_derive.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests mirroring upstream boats_display_derive.rs.
 *
 * Upstream uses the Display derive with custom display format
 * strings. Kotlin has no derive macros, so each test type manually overrides
 * toString() that the macro would generate, then exercises the same
 * runtime invariants.
 */
class BoatsDisplayDeriveTest {
    class UnitError {
        override fun toString(): String = "An error has occurred."
    }

    @Test
    fun unitStructDisplay() {
        assertEquals("An error has occurred.", UnitError().toString())
    }

    data class RecordError(
        val code: UInt,
    ) {
        override fun toString(): String = "Error code: $code"
    }

    @Test
    fun recordStructDisplay() {
        assertEquals("Error code: 0", RecordError(0u).toString())
    }

    data class TupleError(
        val code: Int,
    ) {
        override fun toString(): String = "Error code: $code"
    }

    @Test
    fun tupleStructDisplay() {
        assertEquals("Error code: 2", TupleError(2).toString())
    }

    sealed class EnumError {
        data class StructVariant(
            val code: Int,
        ) : EnumError() {
            override fun toString(): String = "Error code: $code"
        }

        data class TupleVariant(
            val value: String,
        ) : EnumError() {
            override fun toString(): String = "Error: $value"
        }

        object UnitVariant : EnumError() {
            override fun toString(): String = "An error has occurred."
        }
    }

    @Test
    fun enumErrorStructVariant() {
        assertEquals("Error code: 2", EnumError.StructVariant(2).toString())
    }

    @Test
    fun enumErrorTupleVariant() {
        assertEquals("Error: foobar", EnumError.TupleVariant("foobar").toString())
    }

    @Test
    fun enumErrorUnitVariant() {
        assertEquals("An error has occurred.", EnumError.UnitVariant.toString())
    }
}
