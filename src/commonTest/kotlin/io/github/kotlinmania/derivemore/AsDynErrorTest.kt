// port-lint: tests error_tests.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

class AsDynErrorTest {
    @Test
    fun deriveMoreAsDynErrorReturnsSelf() {
        val throwable = Exception("test")
        assertEquals(throwable, throwable.deriveMoreAsDynError())
    }

    @Test
    fun asDynErrorImplementorCanBeCreated() {
        // AsDynError and Sealed are marker interfaces for sealed-trait emulation.
        // They have no methods to test beyond their structural presence.
        val impl =
            object : AsDynError {
                override fun deriveMoreAsDynError(): Throwable = Exception("inner")
            }
        assertEquals("inner", impl.deriveMoreAsDynError().message)
    }
}
