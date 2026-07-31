// port-lint: tests tests/into_iterator.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests mirroring upstream `tests/into_iterator.rs`.
 *
 * Upstream uses `#[derive(IntoIterator)]` to generate `IntoIterator` impls for
 * structs wrapping a collection. Kotlin has no derive macros, so each test
 * type manually implements the `iterator()` / `intoIterator()` method that
 * the macro would generate, then exercises the same runtime invariants.
 */
class IntoIteratorTest {

    class MyVec(private val inner: List<Int>) {
        fun intoIterator(): Iterator<Int> = inner.iterator()
        fun iterator(): Iterator<Int> = inner.iterator()
    }

    @Test
    fun tupleSingleOwnedIterator() {
        val vals = listOf(1, 2, 3)
        val iter = MyVec(vals)
        assertEquals(vals, iter.intoIterator().asSequence().toList())
    }

    class Numbers(val numbers: List<Int>) {
        fun intoIterator(): Iterator<Int> = numbers.iterator()
        fun iterator(): Iterator<Int> = numbers.iterator()
    }

    @Test
    fun namedSingleOwnedIterator() {
        val vals = listOf(1, 2, 3)
        val iter = Numbers(vals)
        assertEquals(vals, iter.intoIterator().asSequence().toList())
    }

    class Generic1<T>(val items: List<T>) {
        fun intoIterator(): Iterator<T> = items.iterator()
    }

    @Test
    fun genericIterator() {
        val vals = listOf(1, 2, 3)
        val iter = Generic1(vals)
        assertEquals(vals, iter.intoIterator().asSequence().toList())
    }

    class Generic4<T>(
        val items: List<T>,
        val useless: Boolean,
    ) {
        fun intoIterator(): Iterator<T> = items.iterator()
    }

    @Test
    fun genericOwnedIterator() {
        val numbers = listOf(1, 2, 3)
        val iter = Generic4(numbers, true)
        assertEquals(numbers, iter.intoIterator().asSequence().toList())
    }
}