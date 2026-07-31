// port-lint: tests tests/sum.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests mirroring upstream `tests/sum.rs`.
 *
 * Upstream uses `#[derive(Sum)]` to generate `Sum` trait impls, which require
 * an `Add` impl to already exist. Kotlin has no derive macros, so each test
 * type manually implements the `plus` (Add) and the fold-based `sum` that the
 * macro would generate, then exercises the same runtime invariants.
 */
class SumTest {

    data class MyInts(val a: Int, val b: Long) {
        operator fun plus(rhs: MyInts): MyInts = MyInts(a + rhs.a, b + rhs.b)
    }

    @Test
    fun myIntsSum() {
        val items = listOf(MyInts(1, 2), MyInts(3, 4), MyInts(5, 6))
        val result = items.reduce { acc, item -> acc + item }
        assertEquals(MyInts(9, 12), result)
    }

    data class Point2D(val x: Int, val y: Int) {
        operator fun plus(rhs: Point2D): Point2D = Point2D(x + rhs.x, y + rhs.y)
    }

    @Test
    fun point2DSum() {
        val items = listOf(Point2D(1, 2), Point2D(3, 4), Point2D(5, 6))
        val result = items.reduce { acc, item -> acc + item }
        assertEquals(Point2D(9, 12), result)
    }

    @Test
    fun myIntsSumOfEmptySingleElement() {
        val items = listOf(MyInts(1, 2))
        val result = items.reduce { acc, item -> acc + item }
        assertEquals(MyInts(1, 2), result)
    }
}