// port-lint: tests from.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests mirroring upstream from.rs.
 *
 * Upstream uses the From derive to generate From trait impls that
 * construct the type from its single field value. Kotlin has no derive
 * macros, so each test type manually implements the constructor that the
 * macro would generate, then exercises the same runtime invariants.
 *
 * Only the struct/enum runtime assertions are ported. The upstream file is
 * 1859 lines — most is compile-time verification of derive expansion with
 * various generic bounds, from(forward), and enum variants. Those have no
 * portable runtime assertions beyond what the basic struct tests cover.
 */
class FromTest {
    object Unit {
        fun from(value: kotlin.Unit): Unit = Unit
    }

    object Tuple {
        fun from(value: kotlin.Unit): Tuple = Tuple
    }

    object Struct {
        fun from(value: kotlin.Unit): Struct = Struct
    }

    @Test
    fun unitFromUnit() {
        assertEquals(Unit, Unit.from(kotlin.Unit))
    }

    @Test
    fun tupleFromUnit() {
        assertEquals(Tuple, Tuple.from(kotlin.Unit))
    }

    @Test
    fun structFromUnit() {
        assertEquals(Struct, Struct.from(kotlin.Unit))
    }

    data class SingleFieldTuple(
        val value: Int,
    ) {
        companion object {
            fun from(value: Int): SingleFieldTuple = SingleFieldTuple(value)
        }
    }

    @Test
    fun singleFieldTupleFromInt() {
        assertEquals(SingleFieldTuple(42), SingleFieldTuple.from(42))
    }

    data class SingleFieldStruct(
        val field: Int,
    ) {
        companion object {
            fun from(value: Int): SingleFieldStruct = SingleFieldStruct(value)
        }
    }

    @Test
    fun singleFieldStructFromInt() {
        assertEquals(SingleFieldStruct(42), SingleFieldStruct.from(42))
    }

    sealed class MixedInts {
        data class SmallInt(
            val value: Int,
        ) : MixedInts()

        data class BigInt(
            val value: Long,
        ) : MixedInts()

        data class TwoSmallInts(
            val a: Int,
            val b: Int,
        ) : MixedInts()

        companion object {
            fun fromSmallInt(value: Int): MixedInts = SmallInt(value)

            fun fromBigInt(value: Long): MixedInts = BigInt(value)

            fun fromTwoSmallInts(a: Int, b: Int): MixedInts = TwoSmallInts(a, b)
        }
    }

    @Test
    fun enumFromSmallInt() {
        assertEquals(MixedInts.SmallInt(42), MixedInts.fromSmallInt(42))
    }

    @Test
    fun enumFromBigInt() {
        assertEquals(MixedInts.BigInt(42L), MixedInts.fromBigInt(42L))
    }

    @Test
    fun enumFromTwoSmallInts() {
        assertEquals(MixedInts.TwoSmallInts(1, 2), MixedInts.fromTwoSmallInts(1, 2))
    }
}
