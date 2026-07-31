// port-lint: tests tests/constructor.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Tests mirroring upstream `tests/constructor.rs`.
 *
 * Upstream uses `#[derive(Constructor)]` to generate a `new()` constructor.
 * Kotlin has no derive macros, so each test type manually implements the
 * constructor that the macro would generate, then exercises the same
 * runtime invariants.
 */
class ConstructorTest {

    class EmptyTuple {
        companion object {
            fun new(): EmptyTuple = EmptyTuple()
        }
    }

    @Test
    fun emptyTupleConstructor() {
        assertNotNull(EmptyTuple.new())
    }

    class EmptyStruct {
        companion object {
            fun new(): EmptyStruct = EmptyStruct()
        }
    }

    @Test
    fun emptyStructConstructor() {
        assertNotNull(EmptyStruct.new())
    }

    class EmptyUnit {
        companion object {
            fun new(): EmptyUnit = EmptyUnit()
        }
    }

    @Test
    fun emptyUnitConstructor() {
        assertNotNull(EmptyUnit.new())
    }

    data class MyInts(val a: Int, val b: Int) {
        companion object {
            fun new(a: Int, b: Int): MyInts = MyInts(a, b)
        }
    }

    @Test
    fun myIntsConstructor() {
        val result = MyInts.new(1, 2)
        assertEquals(1, result.a)
        assertEquals(2, result.b)
    }

    data class Point2D(val x: Int, val y: Int) {
        companion object {
            fun new(x: Int, y: Int): Point2D = Point2D(x, y)
        }
    }

    @Test
    fun point2DConstructor() {
        val result = Point2D.new(-4, 7)
        assertEquals(-4, result.x)
        assertEquals(7, result.y)
    }
}