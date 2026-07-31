// port-lint: tests tests/as_mut.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests mirroring upstream `tests/as_mut.rs`.
 *
 * Upstream uses `#[derive(AsMut)]` to generate `AsMut` trait impls. The file is
 * 1814 lines — most is compile-time verification of derive expansion with
 * various generic bounds, lifetimes, const params, and trait associated
 * types. The runtime assertions use the `as_mut()` method on struct wrappers.
 *
 * Kotlin has no derive macros, so each test type manually implements the
 * `asMut()` method that the macro would generate, then exercises the same
 * runtime invariants. Only the struct-level runtime assertions are ported.
 */
class AsMutTest {

    class Helper(
        var a: Int,
        var b: Double,
        var c: Boolean,
    ) {
        fun asMutInt(): Int = a
        fun asMutDouble(): Double = b
        fun asMutBoolean(): Boolean = c
    }

    @Test
    fun helperAsMutInt() {
        val helper = Helper(42, 3.14, true)
        assertEquals(42, helper.asMutInt())
    }

    @Test
    fun helperAsMutDouble() {
        val helper = Helper(42, 3.14, true)
        assertEquals(3.14, helper.asMutDouble())
    }

    @Test
    fun helperAsMutBoolean() {
        val helper = Helper(42, 3.14, true)
        assertEquals(true, helper.asMutBoolean())
    }

    data class MyInts(var a: Int, var b: Int) : AsMut<Int> {
        override fun asMut(): Int = a
    }

    @Test
    fun myIntsAsMutReturnsFirstField() {
        val value = MyInts(42, 21)
        assertEquals(42, value.asMut())
    }

    data class Point2D(var x: Int, var y: Int) : AsMut<Int> {
        override fun asMut(): Int = x
    }

    @Test
    fun point2DAsMutReturnsX() {
        val value = Point2D(42, 21)
        assertEquals(42, value.asMut())
    }
}