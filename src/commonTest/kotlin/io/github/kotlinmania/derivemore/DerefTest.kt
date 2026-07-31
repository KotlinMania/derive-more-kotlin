// port-lint: tests tests/deref.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests mirroring upstream `tests/deref.rs`.
 *
 * Upstream uses `#[derive(Deref)]` to generate `Deref` trait impls. Most of
 * the file is compile-time verification, but there are three `#[test]`
 * functions with runtime assertions. Kotlin has no derive macros, so each test
 * type manually implements the `getValue` delegation that the macro would
 * generate, then exercises the same runtime invariants.
 */
class DerefTest {

    class GenericVec<T>(private val inner: List<T>) {
        val size: Int get() = inner.size
        fun isEmpty(): Boolean = inner.isEmpty()
    }

    @Test
    fun derefGeneric() {
        val gv = GenericVec<Int>(emptyList())
        assertTrue(gv.isEmpty())
    }

    class GenericBox<T>(private val inner: T) {
        val value: T get() = inner
    }

    @Test
    fun derefGenericForward() {
        val boxed = GenericBox(1)
        assertEquals(1, boxed.value)
    }

    // Upstream enum deref tests: GenericBoxEnum1::Variant2 and
    // GenericBoxEnum3::Variant2 — both assert that the deref target is 1i32.
    sealed class GenericBoxEnum3<out T> {
        data class Variant1<T>(val b: T) : GenericBoxEnum3<T>()
        data class Variant2<T>(val b: T) : GenericBoxEnum3<T>()
        data class Variant3<T>(val flag: Boolean, val b: T) : GenericBoxEnum3<T>()
    }

    @Test
    fun derefGenericForwardEnumOuter() {
        val boxed: GenericBoxEnum3<Int> = GenericBoxEnum3.Variant2(1)
        when (boxed) {
            is GenericBoxEnum3.Variant1 -> assertEquals(1, boxed.b)
            is GenericBoxEnum3.Variant2 -> assertEquals(1, boxed.b)
            is GenericBoxEnum3.Variant3 -> assertEquals(1, boxed.b)
        }
    }
}