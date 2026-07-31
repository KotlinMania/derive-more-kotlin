// port-lint: tests tests/mul.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests mirroring upstream `tests/mul.rs`.
 *
 * Upstream uses `#[derive(Mul)]` to generate multiplication operator impls.
 * Kotlin has no derive macros, so each test type manually implements the
 * `times` operator that the macro would generate, then exercises the same
 * runtime invariants.
 */
class MulTest {

    // --- structs / scalar ---

    data class MyInt(val value: Int) {
        operator fun times(rhs: Int): MyInt = MyInt(value * rhs)
    }

    @Test
    fun singleFieldTupleScalarMul() {
        assertEquals(MyInt(-5), MyInt(-1) * 5)
    }

    data class MyInts(val a: Int, val b: Int) {
        operator fun times(rhs: Int): MyInts = MyInts(a * rhs, b * rhs)
    }

    @Test
    fun multiFieldTupleScalarMul() {
        assertEquals(MyInts(-5, 15), MyInts(-1, 3) * 5)
    }

    data class Point1D(val x: Int) {
        operator fun times(rhs: Int): Point1D = Point1D(x * rhs)
    }

    @Test
    fun singleFieldStructScalarMul() {
        assertEquals(Point1D(-5), Point1D(-1) * 5)
    }

    data class Point2D(val x: Int, val y: Int) {
        operator fun times(rhs: Int): Point2D = Point2D(x * rhs, y * rhs)
    }

    @Test
    fun multiFieldStructScalarMul() {
        assertEquals(Point2D(-5, 15), Point2D(-1, 3) * 5)
    }

    // --- structs / structural (forward) ---

    data class MyIntForward(val value: Int) {
        operator fun times(rhs: MyIntForward): MyIntForward = MyIntForward(value * rhs.value)
    }

    @Test
    fun singleFieldTupleForwardMul() {
        assertEquals(MyIntForward(-5), MyIntForward(-1) * MyIntForward(5))
    }

    data class MyIntsForward(val a: Int, val b: Int) {
        operator fun times(rhs: MyIntsForward): MyIntsForward =
            MyIntsForward(a * rhs.a, b * rhs.b)
    }

    @Test
    fun multiFieldTupleForwardMul() {
        assertEquals(MyIntsForward(-3, 15), MyIntsForward(-1, 3) * MyIntsForward(3, 5))
    }

    data class Point1DForward(val x: Int) {
        operator fun times(rhs: Point1DForward): Point1DForward = Point1DForward(x * rhs.x)
    }

    @Test
    fun singleFieldStructForwardMul() {
        assertEquals(Point1DForward(-5), Point1DForward(-1) * Point1DForward(5))
    }

    data class Point2DForward(val x: Int, val y: Int) {
        operator fun times(rhs: Point2DForward): Point2DForward =
            Point2DForward(x * rhs.x, y * rhs.y)
    }

    @Test
    fun multiFieldStructForwardMul() {
        assertEquals(Point2DForward(-3, 15), Point2DForward(-1, 3) * Point2DForward(3, 5))
    }

    // --- enums / structural (forward) ---

    sealed class MixedInts {
        data class SmallInt(val value: Int) : MixedInts()
        data class BigInt(val value: Long) : MixedInts()
        data class TwoSmallInts(val a: Int, val b: Int) : MixedInts()
        data class NamedSmallInts(val x: Int, val y: Int) : MixedInts()
        data class UnsignedOne(val value: UInt) : MixedInts()
        data class UnsignedTwo(val value: UInt) : MixedInts()
        object Unit : MixedInts()
    }

    private operator fun MixedInts.times(rhs: MixedInts): Result<MixedInts> =
        when {
            this is MixedInts.SmallInt && rhs is MixedInts.SmallInt ->
                Result.success(MixedInts.SmallInt(value * rhs.value))
            this is MixedInts.BigInt && rhs is MixedInts.BigInt ->
                Result.success(MixedInts.BigInt(value * rhs.value))
            this is MixedInts.TwoSmallInts && rhs is MixedInts.TwoSmallInts ->
                Result.success(MixedInts.TwoSmallInts(a * rhs.a, b * rhs.b))
            this is MixedInts.NamedSmallInts && rhs is MixedInts.NamedSmallInts ->
                Result.success(MixedInts.NamedSmallInts(x * rhs.x, y * rhs.y))
            this is MixedInts.UnsignedOne && rhs is MixedInts.UnsignedOne ->
                Result.success(MixedInts.UnsignedOne(value * rhs.value))
            this is MixedInts.UnsignedTwo && rhs is MixedInts.UnsignedTwo ->
                Result.success(MixedInts.UnsignedTwo(value * rhs.value))
            this is MixedInts.Unit && rhs is MixedInts.Unit ->
                Result.failure(UnitError.new("mul"))
            else ->
                Result.failure(WrongVariantError.new("mul"))
        }

    @Test
    fun enumForwardSmallIntMul() {
        assertEquals(MixedInts.SmallInt(-2), (MixedInts.SmallInt(-1) * MixedInts.SmallInt(2)).getOrThrow())
    }

    @Test
    fun enumForwardBigIntMul() {
        assertEquals(MixedInts.BigInt(-2), (MixedInts.BigInt(-1) * MixedInts.BigInt(2)).getOrThrow())
    }

    @Test
    fun enumForwardTwoSmallIntsMul() {
        assertEquals(
            MixedInts.TwoSmallInts(-2, -15),
            (MixedInts.TwoSmallInts(-1, 3) * MixedInts.TwoSmallInts(2, -5)).getOrThrow(),
        )
    }

    @Test
    fun enumForwardNamedSmallIntsMul() {
        assertEquals(
            MixedInts.NamedSmallInts(-2, -15),
            (MixedInts.NamedSmallInts(-1, 3) * MixedInts.NamedSmallInts(2, -5)).getOrThrow(),
        )
    }

    @Test
    fun enumForwardUnsignedOneMul() {
        assertEquals(
            MixedInts.UnsignedOne(2u),
            (MixedInts.UnsignedOne(1u) * MixedInts.UnsignedOne(2u)).getOrThrow(),
        )
    }

    @Test
    fun enumForwardUnsignedTwoMul() {
        assertEquals(
            MixedInts.UnsignedTwo(2u),
            (MixedInts.UnsignedTwo(1u) * MixedInts.UnsignedTwo(2u)).getOrThrow(),
        )
    }

    @Test
    fun enumForwardUnitMulReturnsUnitError() {
        val error = (MixedInts.Unit * MixedInts.Unit).exceptionOrNull()!!
        assertEquals("Cannot mul() unit variants", error.message)
    }

    @Test
    fun enumForwardMismatchedVariantsReturnsWrongVariantError() {
        val error = (MixedInts.SmallInt(-1) * MixedInts.BigInt(2)).exceptionOrNull()!!
        assertEquals("Trying to mul() mismatched enum variants", error.message)
    }
}