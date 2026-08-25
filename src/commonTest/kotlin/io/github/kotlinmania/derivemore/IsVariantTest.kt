// port-lint: tests is_variant.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests mirroring upstream is_variant.rs.
 *
 * Upstream uses the IsVariant derive to generate is-variant methods.
 * Kotlin has no derive macros, so each test enum manually implements the
 * is-variant methods that the macro would generate, then exercises the same
 * runtime invariants.
 */
class IsVariantTest {
    sealed class Either<out TLeft, out TRight> {
        data class Left<TLeft>(
            val value: TLeft,
        ) : Either<TLeft, Nothing>()

        data class Right<TRight>(
            val value: TRight,
        ) : Either<Nothing, TRight>()
    }

    private fun <L, R> Either<L, R>.isLeft(): Boolean = this is Either.Left

    private fun <L, R> Either<L, R>.isRight(): Boolean = this is Either.Right

    sealed class Maybe<out T> {
        object Nothing : Maybe<Nothing>()

        data class Just<T>(
            val value: T,
        ) : Maybe<T>()
    }

    private fun <T> Maybe<T>.isJust(): Boolean = this is Maybe.Just

    private fun <T> Maybe<T>.isNothing(): Boolean = this is Maybe.Nothing

    sealed class Color {
        data class Rgb(
            val r: Int,
            val g: Int,
            val b: Int,
        ) : Color()

        data class Cmyk(
            val c: Int,
            val m: Int,
            val y: Int,
            val k: Int,
        ) : Color()
    }

    private fun Color.isRgb(): Boolean = this is Color.Rgb

    private fun Color.isCmyk(): Boolean = this is Color.Cmyk

    sealed class WithConstraints<out T> {
        data class One<T>(
            val value: T,
        ) : WithConstraints<T>()

        object Two : WithConstraints<Nothing>()
    }

    private fun <T> WithConstraints<T>.isOne(): Boolean = this is WithConstraints.One

    private fun <T> WithConstraints<T>.isTwo(): Boolean = this is WithConstraints.Two

    sealed class KitchenSink<out T1, out T2> {
        data class Left<T1>(
            val value: T1,
        ) : KitchenSink<T1, Nothing>()

        data class Right<T2>(
            val value: T2,
        ) : KitchenSink<Nothing, T2>()

        data class OwnBoth<T1, T2>(
            val left: T1,
            val right: T2,
        ) : KitchenSink<T1, T2>()

        object Empty : KitchenSink<Nothing, Nothing>()

        class NeverMind : KitchenSink<Nothing, Nothing>()

        class NothingToSeeHere : KitchenSink<Nothing, Nothing>()
    }

    private fun <T1, T2> KitchenSink<T1, T2>.isLeft(): Boolean = this is KitchenSink.Left

    private fun <T1, T2> KitchenSink<T1, T2>.isRight(): Boolean = this is KitchenSink.Right

    private fun <T1, T2> KitchenSink<T1, T2>.isOwnBoth(): Boolean = this is KitchenSink.OwnBoth

    private fun <T1, T2> KitchenSink<T1, T2>.isEmpty(): Boolean = this is KitchenSink.Empty

    private fun <T1, T2> KitchenSink<T1, T2>.isNeverMind(): Boolean = this is KitchenSink.NeverMind

    private fun <T1, T2> KitchenSink<T1, T2>.isNothingToSeeHere(): Boolean = this is KitchenSink.NothingToSeeHere

    @Test
    fun eitherRightIsRightAndNotLeft() {
        val either: Either<Int, Int> = Either.Right(7)
        assertTrue(either.isRight())
        assertFalse(either.isLeft())
    }

    @Test
    fun eitherLeftIsLeftAndNotRight() {
        val either: Either<Int, Int> = Either.Left(7)
        assertFalse(either.isRight())
        assertTrue(either.isLeft())
    }

    @Test
    fun maybeJustIsJustAndNotNothing() {
        val maybe: Maybe<Int> = Maybe.Just(7)
        assertTrue(maybe.isJust())
        assertFalse(maybe.isNothing())
    }

    @Test
    fun maybeNothingIsNothingAndNotJust() {
        val maybe = Maybe.Nothing
        assertFalse(maybe.isJust())
        assertTrue(maybe.isNothing())
    }

    @Test
    fun colorRgbIsRgbAndNotCmyk() {
        val color = Color.Rgb(0, 0, 0)
        assertTrue(color.isRgb())
        assertFalse(color.isCmyk())
    }

    @Test
    fun colorCmykIsCmykAndNotRgb() {
        val color = Color.Cmyk(0, 0, 0, 0)
        assertFalse(color.isRgb())
        assertTrue(color.isCmyk())
    }

    @Test
    fun withConstraintsOneIsOneAndNotTwo() {
        val wc: WithConstraints<Int> = WithConstraints.One(1)
        assertTrue(wc.isOne())
        assertFalse(wc.isTwo())
    }

    @Test
    fun withConstraintsTwoIsTwoAndNotOne() {
        val wc: WithConstraints<Int> = WithConstraints.Two
        assertFalse(wc.isOne())
        assertTrue(wc.isTwo())
    }

    @Test
    fun kitchenSinkLeftIsLeft() {
        val ks: KitchenSink<Int, Int> = KitchenSink.Left<Int>(1)
        assertTrue(ks.isLeft())
        assertFalse(ks.isRight())
        assertFalse(ks.isOwnBoth())
        assertFalse(ks.isEmpty())
        assertFalse(ks.isNeverMind())
        assertFalse(ks.isNothingToSeeHere())
    }

    @Test
    fun kitchenSinkRightIsRight() {
        val ks: KitchenSink<Int, Int> = KitchenSink.Right<Int>(1)
        assertFalse(ks.isLeft())
        assertTrue(ks.isRight())
        assertFalse(ks.isOwnBoth())
        assertFalse(ks.isEmpty())
        assertFalse(ks.isNeverMind())
        assertFalse(ks.isNothingToSeeHere())
    }

    @Test
    fun kitchenSinkOwnBothIsOwnBoth() {
        val ks: KitchenSink<Int, Int> = KitchenSink.OwnBoth<Int, Int>(1, 2)
        assertFalse(ks.isLeft())
        assertFalse(ks.isRight())
        assertTrue(ks.isOwnBoth())
        assertFalse(ks.isEmpty())
        assertFalse(ks.isNeverMind())
        assertFalse(ks.isNothingToSeeHere())
    }

    @Test
    fun kitchenSinkEmptyIsEmpty() {
        val ks: KitchenSink<Int, Int> = KitchenSink.Empty
        assertFalse(ks.isLeft())
        assertFalse(ks.isRight())
        assertFalse(ks.isOwnBoth())
        assertTrue(ks.isEmpty())
        assertFalse(ks.isNeverMind())
        assertFalse(ks.isNothingToSeeHere())
    }

    @Test
    fun kitchenSinkNeverMindIsNeverMind() {
        val ks: KitchenSink<Int, Int> = KitchenSink.NeverMind()
        assertFalse(ks.isLeft())
        assertFalse(ks.isRight())
        assertFalse(ks.isOwnBoth())
        assertFalse(ks.isEmpty())
        assertTrue(ks.isNeverMind())
        assertFalse(ks.isNothingToSeeHere())
    }

    @Test
    fun kitchenSinkNothingToSeeHereIsNothingToSeeHere() {
        val ks: KitchenSink<Int, Int> = KitchenSink.NothingToSeeHere()
        assertFalse(ks.isLeft())
        assertFalse(ks.isRight())
        assertFalse(ks.isOwnBoth())
        assertFalse(ks.isEmpty())
        assertFalse(ks.isNeverMind())
        assertTrue(ks.isNothingToSeeHere())
    }
}
