// port-lint: tests not.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests mirroring upstream not.rs.
 *
 * Upstream uses the Not derive to generate bitwise-NOT/logical-NOT operator
 * impls. Kotlin has no derive macros, so each test type manually implements
 * the unaryNot/inv operator that the macro would generate, then exercises
 * the same runtime invariants.
 */
class NotTest {
    data class MyInts(
        val a: Int,
        val b: Int,
    ) {
        operator fun not(): MyInts = MyInts(a.inv(), b.inv())
    }

    @Test
    fun multiFieldTupleNot() {
        val input = MyInts(12, 21)
        val result = !input
        assertEquals(MyInts(12.inv(), 21.inv()), result)
    }

    data class Point2D(
        val x: Int,
        val y: Int,
    ) {
        operator fun not(): Point2D = Point2D(x.inv(), y.inv())
    }

    @Test
    fun multiFieldStructNot() {
        val input = Point2D(12, 21)
        val result = !input
        assertEquals(Point2D(12.inv(), 21.inv()), result)
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

        data class NamedSmallInts(
            val x: Int,
            val y: Int,
        ) : MixedInts()

        data class UnsignedOne(
            val value: UInt,
        ) : MixedInts()

        data class UnsignedTwo(
            val value: UInt,
        ) : MixedInts()
    }

    private operator fun MixedInts.not(): MixedInts =
        when (this) {
            is MixedInts.SmallInt -> MixedInts.SmallInt(value.inv())
            is MixedInts.BigInt -> MixedInts.BigInt(value.inv())
            is MixedInts.TwoSmallInts -> MixedInts.TwoSmallInts(a.inv(), b.inv())
            is MixedInts.NamedSmallInts -> MixedInts.NamedSmallInts(x.inv(), y.inv())
            is MixedInts.UnsignedOne -> MixedInts.UnsignedOne(value.inv())
            is MixedInts.UnsignedTwo -> MixedInts.UnsignedTwo(value.inv())
        }

    @Test
    fun enumSmallIntNot() {
        val input = MixedInts.SmallInt(12)
        val result = !input
        assertEquals(MixedInts.SmallInt(12.inv()), result)
    }

    @Test
    fun enumBigIntNot() {
        val input = MixedInts.BigInt(12L)
        val result = !input
        assertEquals(MixedInts.BigInt(12L.inv()), result)
    }

    @Test
    fun enumTwoSmallIntsNot() {
        val input = MixedInts.TwoSmallInts(12, 21)
        val result = !input
        assertEquals(MixedInts.TwoSmallInts(12.inv(), 21.inv()), result)
    }

    @Test
    fun enumNamedSmallIntsNot() {
        val input = MixedInts.NamedSmallInts(12, 21)
        val result = !input
        assertEquals(MixedInts.NamedSmallInts(12.inv(), 21.inv()), result)
    }

    @Test
    fun enumUnsignedOneNot() {
        val input = MixedInts.UnsignedOne(12u)
        val result = !input
        assertEquals(MixedInts.UnsignedOne(12u.inv()), result)
    }

    @Test
    fun enumUnsignedTwoNot() {
        val input = MixedInts.UnsignedTwo(12u)
        val result = !input
        assertEquals(MixedInts.UnsignedTwo(12u.inv()), result)
    }

    sealed class EnumWithUnit {
        data class SmallInt(
            val value: Int,
        ) : EnumWithUnit()

        object Unit : EnumWithUnit()
    }

    private operator fun EnumWithUnit.not(): EnumWithUnit =
        when (this) {
            is EnumWithUnit.SmallInt -> EnumWithUnit.SmallInt(value.inv())
            is EnumWithUnit.Unit -> throw UnitError.new("not")
        }
}
