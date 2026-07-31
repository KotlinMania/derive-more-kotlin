// port-lint: tests tests/mul_assign.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests mirroring upstream `tests/mul_assign.rs`.
 *
 * Upstream uses `#[derive(MulAssign)]` to generate `*=` operator impls.
 * Kotlin has no derive macros, so each test type manually implements the
 * `timesAssign` operator that the macro would generate, then exercises the
 * same runtime invariants.
 */
class MulAssignTest {

    // --- scalar ---

    data class MyInt(var value: Int) {
        operator fun timesAssign(rhs: Int) {
            value *= rhs
        }
    }

    @Test
    fun singleFieldTupleScalarMulAssign() {
        val a = MyInt(-1)
        a *= 5
        assertEquals(MyInt(-5), a)
    }

    data class MyInts(var a: Int, var b: Int) {
        operator fun timesAssign(rhs: Int) {
            a *= rhs
            b *= rhs
        }
    }

    @Test
    fun multiFieldTupleScalarMulAssign() {
        val a = MyInts(-1, 3)
        a *= 5
        assertEquals(MyInts(-5, 15), a)
    }

    data class Point1D(var x: Int) {
        operator fun timesAssign(rhs: Int) {
            x *= rhs
        }
    }

    @Test
    fun singleFieldStructScalarMulAssign() {
        val a = Point1D(-1)
        a *= 5
        assertEquals(Point1D(-5), a)
    }

    data class Point2D(var x: Int, var y: Int) {
        operator fun timesAssign(rhs: Int) {
            x *= rhs
            y *= rhs
        }
    }

    @Test
    fun multiFieldStructScalarMulAssign() {
        val a = Point2D(-1, 3)
        a *= 5
        assertEquals(Point2D(-5, 15), a)
    }

    // --- structural (forward) ---

    data class MyIntForward(var value: Int) {
        operator fun timesAssign(rhs: MyIntForward) {
            value *= rhs.value
        }
    }

    @Test
    fun singleFieldTupleForwardMulAssign() {
        val a = MyIntForward(-1)
        a *= MyIntForward(5)
        assertEquals(MyIntForward(-5), a)
    }

    data class MyIntsForward(var a: Int, var b: Int) {
        operator fun timesAssign(rhs: MyIntsForward) {
            a *= rhs.a
            b *= rhs.b
        }
    }

    @Test
    fun multiFieldTupleForwardMulAssign() {
        val a = MyIntsForward(-1, 3)
        a *= MyIntsForward(3, 5)
        assertEquals(MyIntsForward(-3, 15), a)
    }

    data class Point1DForward(var x: Int) {
        operator fun timesAssign(rhs: Point1DForward) {
            x *= rhs.x
        }
    }

    @Test
    fun singleFieldStructForwardMulAssign() {
        val a = Point1DForward(-1)
        a *= Point1DForward(5)
        assertEquals(Point1DForward(-5), a)
    }

    data class Point2DForward(var x: Int, var y: Int) {
        operator fun timesAssign(rhs: Point2DForward) {
            x *= rhs.x
            y *= rhs.y
        }
    }

    @Test
    fun multiFieldStructForwardMulAssign() {
        val a = Point2DForward(-1, 3)
        a *= Point2DForward(3, 5)
        assertEquals(Point2DForward(-3, 15), a)
    }
}