// port-lint: tests unwrap.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Tests mirroring upstream unwrap.rs.
 *
 * Upstream uses the Unwrap derive to generate unwrap methods on enum variants.
 * Kotlin has no derive macros, so each test enum manually implements the
 * unwrap methods that the macro would generate, then exercises the same
 * runtime invariants.
 */
class UnwrapTest {
    sealed class Maybe<out T> {
        object Nothing : Maybe<Nothing>()

        data class Just<T>(
            val value: T,
        ) : Maybe<T>()
    }

    private fun <T> Maybe<T>.unwrapJust(): T =
        when (this) {
            is Maybe.Just -> value
            is Maybe.Nothing -> throw WrongVariantError.new("unwrapJust")
        }

    private fun Maybe<*>.unwrapNothing(): Unit =
        when (this) {
            is Maybe.Nothing -> Unit
            is Maybe.Just -> throw WrongVariantError.new("unwrapNothing")
        }

    private fun <T> Maybe<T>.unwrapJustRef(): T = unwrapJust()

    private fun Maybe<*>.unwrapNothingRef(): Unit = unwrapNothing()

    sealed class Tuple<out T> {
        object None : Tuple<Nothing>()

        data class Single<T>(
            val value: T,
        ) : Tuple<T>()

        data class Double<T>(
            val first: T,
            val second: T,
        ) : Tuple<T>()

        data class Triple<T>(
            val a: T,
            val b: T,
            val c: T,
        ) : Tuple<T>()
    }

    private fun <T> Tuple<T>.unwrapDoubleMut(): Pair<T, T> =
        when (this) {
            is Tuple.Double -> first to second
            is Tuple.None -> throw WrongVariantError.new("unwrapDoubleMut")
            is Tuple.Single -> throw WrongVariantError.new("unwrapDoubleMut")
            is Tuple.Triple -> throw WrongVariantError.new("unwrapDoubleMut")
        }

    private fun <T> Tuple<T>.unwrapSingleMut(): T =
        when (this) {
            is Tuple.Single -> value
            is Tuple.None -> throw WrongVariantError.new("unwrapSingleMut")
            is Tuple.Double -> throw WrongVariantError.new("unwrapSingleMut")
            is Tuple.Triple -> throw WrongVariantError.new("unwrapSingleMut")
        }

    @Test
    fun unwrapNothingOnNothingReturnsUnit() {
        assertEquals(Unit, Maybe.Nothing.unwrapNothing())
    }

    @Test
    fun unwrapJustOnJustReturnsValue() {
        assertEquals(1, Maybe.Just(1).unwrapJust())
    }

    @Test
    fun unwrapJustRefOnJustReturnsValue() {
        assertEquals(42, Maybe.Just(42).unwrapJustRef())
    }

    @Test
    fun unwrapJustMutOnJustReturnsValue() {
        assertEquals(42, Maybe.Just(42).unwrapJustRef())
    }

    @Test
    fun unwrapJustOnNothingPanics() {
        assertFailsWith(Exception::class) {
            Maybe.Nothing.unwrapJust()
        }
    }

    @Test
    fun unwrapNothingOnJustPanics() {
        assertFailsWith(Exception::class) {
            Maybe.Just(2).unwrapNothing()
        }
    }

    @Test
    fun unwrapNothingRefOnJustPanics() {
        assertFailsWith(Exception::class) {
            Maybe.Just(2).unwrapNothingRef()
        }
    }

    @Test
    fun unwrapDoubleMutReturnsBothValues() {
        val value = Tuple.Double(1, 12)
        val (a, b) = value.unwrapDoubleMut()
        assertEquals(1, a)
        assertEquals(12, b)
    }

    @Test
    fun unwrapSingleMutReturnsValue() {
        val value = Tuple.Single(128)
        val x = value.unwrapSingleMut()
        assertEquals(128, x)
    }
}
