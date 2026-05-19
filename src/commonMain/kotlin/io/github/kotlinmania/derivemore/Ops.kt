// port-lint: source ops.rs
package io.github.kotlinmania.derivemore

/**
 * Error returned by generated implementations when an arithmetic or logic
 * operation is invoked on a unit-like variant of an enum.
 */
class UnitError private constructor(
    private val operationName: String,
) : Exception("Cannot ${operationName}() unit variants") {
    companion object {
        fun new(operationName: String): UnitError = UnitError(operationName)
    }

    override fun toString(): String = message ?: ""
}
