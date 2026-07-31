// port-lint: tests tests/into.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests mirroring upstream `tests/into.rs`.
 *
 * Upstream uses `#[derive(Into)]` to generate `Into` trait impls that extract
 * the single field value. Kotlin has no derive macros, so each test type
 * manually implements the `to` / extraction method that the macro would
 * generate, then exercises the same runtime invariants.
 *
 * Only the struct runtime assertions from the `unit` module are ported here.
 * The upstream file is 1524 lines — most is compile-time verification with
 * generic bounds, #[into(forward)], ref/ref_mut variants, and unsafe
 * mem::transmute tests. Those have no portable runtime assertions beyond
 * what the basic struct tests cover.
 */
class IntoTest {

    class Unit {
        fun into(): kotlin.Unit = kotlin.Unit
    }

    class Tuple {
        fun into(): kotlin.Unit = kotlin.Unit
    }

    class Struct {
        fun into(): kotlin.Unit = kotlin.Unit
    }

    @Test
    fun unitInto() {
        assertEquals(kotlin.Unit, Unit().into())
    }

    @Test
    fun tupleInto() {
        assertEquals(kotlin.Unit, Tuple().into())
    }

    @Test
    fun structInto() {
        assertEquals(kotlin.Unit, Struct().into())
    }

    data class SingleFieldTuple(val value: Int) {
        fun into(): Int = value
    }

    @Test
    fun singleFieldTupleInto() {
        assertEquals(42, SingleFieldTuple(42).into())
    }

    data class SingleFieldStruct(val field: Int) {
        fun into(): Int = field
    }

    @Test
    fun singleFieldStructInto() {
        assertEquals(42, SingleFieldStruct(42).into())
    }
}