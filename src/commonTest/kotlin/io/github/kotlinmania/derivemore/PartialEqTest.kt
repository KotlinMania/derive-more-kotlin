// port-lint: tests partial_eq.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * Tests mirroring upstream partial_eq.rs.
 *
 * Upstream uses the PartialEq derive to generate PartialEq impls.
 * Kotlin data classes auto-generate equals/hashCode, so the derived
 * behavior is already available. These tests exercise the same equality
 * invariants the upstream tests verify.
 */
class PartialEqTest {
    object Baz

    @Test
    fun unitStructEqualsSelf() {
        assertEquals(Baz, Baz)
    }

    class EmptyTuple {
        override fun equals(other: Any?): Boolean = other is EmptyTuple

        override fun hashCode(): Int = EmptyTuple::class.hashCode()
    }

    @Test
    fun emptyTupleEqualsSelf() {
        assertEquals(EmptyTuple(), EmptyTuple())
    }

    class EmptyStruct {
        override fun equals(other: Any?): Boolean = other is EmptyStruct

        override fun hashCode(): Int = EmptyStruct::class.hashCode()
    }

    @Test
    fun emptyStructEqualsSelf() {
        assertEquals(EmptyStruct(), EmptyStruct())
    }

    data class MultiFieldTuple(
        val a: Boolean,
        val b: Int,
    )

    @Test
    fun multiFieldTupleEquality() {
        assertEquals(MultiFieldTuple(true, 0), MultiFieldTuple(true, 0))
        assertNotEquals(MultiFieldTuple(true, 0), MultiFieldTuple(false, 0))
        assertNotEquals(MultiFieldTuple(true, 0), MultiFieldTuple(true, 1))
        assertNotEquals(MultiFieldTuple(true, 0), MultiFieldTuple(false, 1))
    }

    data class MultiFieldStruct(
        val b: Boolean,
        val i: Int,
    )

    @Test
    fun multiFieldStructEquality() {
        assertEquals(MultiFieldStruct(true, 0), MultiFieldStruct(true, 0))
        assertNotEquals(MultiFieldStruct(true, 0), MultiFieldStruct(false, 0))
        assertNotEquals(MultiFieldStruct(true, 0), MultiFieldStruct(true, 1))
        assertNotEquals(MultiFieldStruct(true, 0), MultiFieldStruct(false, 1))
    }

    data class RecursiveTuple(
        val first: RecursiveTuple?,
        val rest: List<RecursiveTuple>,
    )

    @Test
    fun recursiveTupleEquality() {
        assertEquals(RecursiveTuple(null, emptyList()), RecursiveTuple(null, emptyList()))
        assertNotEquals(
            RecursiveTuple(null, emptyList()),
            RecursiveTuple(null, listOf(RecursiveTuple(null, emptyList()))),
        )
    }
}
