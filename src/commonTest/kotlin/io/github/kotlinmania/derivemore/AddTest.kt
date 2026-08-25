// port-lint: tests add.rs
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

    // --- struct addition (manually implementing what Add generates) ---

    data class MyInts(
        val a: Int,
        val b: Int,
    ) {
        operator fun plus(rhs: MyInts): MyInts = MyInts(a + rhs.a, b + rhs.b)
    }

    @Test
    fun multiFieldTupleAdd() {
        assertEquals(MyInts(13, 23), MyInts(12, 21) + MyInts(1, 2))
    }

    data class Point2D(
        val x: Int,
        val y: Int,
    ) {
        operator fun plus(rhs: Point2D): Point2D = Point2D(x + rhs.x, y + rhs.y)
    }

    @Test
    fun multiFieldStructAdd() {
        assertEquals(Point2D(13, 23), Point2D(12, 21) + Point2D(1, 2))
    }

    // --- enum addition (forward mode) ---

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

        object Unit : MixedInts()
    }

    private operator fun MixedInts.plus(rhs: MixedInts): Result<MixedInts> =
        when {
            this is MixedInts.SmallInt && rhs is MixedInts.SmallInt ->
                Result.success(MixedInts.SmallInt(value + rhs.value))
            this is MixedInts.BigInt && rhs is MixedInts.BigInt ->
                Result.success(MixedInts.BigInt(value + rhs.value))
            this is MixedInts.TwoSmallInts && rhs is MixedInts.TwoSmallInts ->
                Result.success(MixedInts.TwoSmallInts(a + rhs.a, b + rhs.b))
            this is MixedInts.NamedSmallInts && rhs is MixedInts.NamedSmallInts ->
                Result.success(MixedInts.NamedSmallInts(x + rhs.x, y + rhs.y))
            this is MixedInts.UnsignedOne && rhs is MixedInts.UnsignedOne ->
                Result.success(MixedInts.UnsignedOne(value + rhs.value))
            this is MixedInts.UnsignedTwo && rhs is MixedInts.UnsignedTwo ->
                Result.success(MixedInts.UnsignedTwo(value + rhs.value))
            this is MixedInts.Unit && rhs is MixedInts.Unit ->
                Result.failure(UnitError.new("add"))
            else ->
                Result.failure(WrongVariantError.new("add"))
        }

    @Test
    fun enumSmallIntAdd() {
        assertEquals(
            MixedInts.SmallInt(1),
            (MixedInts.SmallInt(-1) + MixedInts.SmallInt(2)).getOrThrow(),
        )
    }

    @Test
    fun enumBigIntAdd() {
        assertEquals(
            MixedInts.BigInt(1),
            (MixedInts.BigInt(-1) + MixedInts.BigInt(2)).getOrThrow(),
        )
    }

    @Test
    fun enumTwoSmallIntsAdd() {
        assertEquals(
            MixedInts.TwoSmallInts(1, -2),
            (MixedInts.TwoSmallInts(-1, 3) + MixedInts.TwoSmallInts(2, -5)).getOrThrow(),
        )
    }

    @Test
    fun enumNamedSmallIntsAdd() {
        assertEquals(
            MixedInts.NamedSmallInts(1, -2),
            (MixedInts.NamedSmallInts(-1, 3) + MixedInts.NamedSmallInts(2, -5)).getOrThrow(),
        )
    }

    @Test
    fun enumUnsignedOneAdd() {
        assertEquals(
            MixedInts.UnsignedOne(3u),
            (MixedInts.UnsignedOne(1u) + MixedInts.UnsignedOne(2u)).getOrThrow(),
        )
    }

    @Test
    fun enumUnsignedTwoAdd() {
        assertEquals(
            MixedInts.UnsignedTwo(3u),
            (MixedInts.UnsignedTwo(1u) + MixedInts.UnsignedTwo(2u)).getOrThrow(),
        )
    }

    @Test
    fun enumUnitAddReturnsUnitError() {
        val error = (MixedInts.Unit + MixedInts.Unit).exceptionOrNull()!!
        assertEquals("Cannot add() unit variants", error.message)
    }

    @Test
    fun enumMismatchedVariantsAddReturnsWrongVariantError() {
        val error = (MixedInts.SmallInt(-1) + MixedInts.BigInt(2)).exceptionOrNull()!!
        assertEquals("Trying to add() mismatched enum variants", error.message)
    }
}
