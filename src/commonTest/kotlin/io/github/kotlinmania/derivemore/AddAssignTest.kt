// port-lint: tests tests/add_assign.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests mirroring upstream `tests/add_assign.rs`.
 *
 * Upstream uses `#[derive(AddAssign)]` to generate `+=` operator impls.
 * Kotlin has no derive macros, so each test type manually implements the
 * `plusAssign` operator that the macro would generate, then exercises the
 * same runtime invariants.
 */
class AddAssignTest {

    data class MyInts(var a: Int, var b: Int) {
        operator fun plusAssign(rhs: MyInts) {
            a += rhs.a
            b += rhs.b
        }
    }

    @Test
    fun multiFieldTupleAddAssign() {
        val value = MyInts(12, 21)
        value += MyInts(1, 2)
        assertEquals(MyInts(13, 23), value)
    }

    data class Point2D(var x: Int, var y: Int) {
        operator fun plusAssign(rhs: Point2D) {
            x += rhs.x
            y += rhs.y
        }
    }

    @Test
    fun multiFieldStructAddAssign() {
        val value = Point2D(12, 21)
        value += Point2D(1, 2)
        assertEquals(Point2D(13, 23), value)
    }
}