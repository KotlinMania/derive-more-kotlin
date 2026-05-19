// port-lint: source add.rs
package io.github.kotlinmania.derivemore

/**
 * Definitions used in generated implementations of Add-like operator traits.
 */

/**
 * Error returned by generated implementations when an arithmetic or logic
 * operation is invoked on mismatched enum variants.
 */
class WrongVariantError private constructor(
    private val operationName: String,
) : Exception("Trying to ${operationName}() mismatched enum variants") {
    companion object {
        fun new(operationName: String): WrongVariantError = WrongVariantError(operationName)
    }

    override fun toString(): String = message ?: ""
}

/**
 * Possible errors returned by generated implementations of binary arithmetic
 * or logic operations.
 */
sealed class BinaryError private constructor(
    private val displayValue: String,
    private val sourceValue: Throwable?,
) : Exception(displayValue) {
    /**
     * Operation is attempted between mismatched enum variants.
     */
    class Mismatch(
        val error: WrongVariantError,
    ) : BinaryError(error.toString(), error)

    /**
     * Operation is attempted on unit-like enum variants.
     */
    class Unit(
        val error: UnitError,
    ) : BinaryError(error.toString(), error)

    fun source(): Throwable? = sourceValue

    override fun toString(): String = displayValue
}
