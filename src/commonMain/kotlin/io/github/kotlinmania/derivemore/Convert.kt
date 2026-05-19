// port-lint: source convert.rs
package io.github.kotlinmania.derivemore

/**
 * Definitions used in generated implementations of conversion traits.
 *
 * Upstream exposes the repr-conversion error when the try-from feature is
 * enabled and the value-conversion error when the try-into feature is enabled.
 */

/**
 * Error returned by a generated TryFrom implementation on enums to convert
 * from their representation.
 */
class TryFromReprError<T> private constructor(
    /**
     * Original input value which failed to convert via the generated TryFrom
     * implementation.
     */
    val input: T,
) {
    companion object {
        fun <T> new(input: T): TryFromReprError<T> = TryFromReprError(input)
    }

    override fun toString(): String = "`$input` does not correspond to a unit variant"

    override fun equals(other: Any?): Boolean =
        other is TryFromReprError<*> && input == other.input

    override fun hashCode(): Int = input?.hashCode() ?: 0
}

/**
 * Error returned by a generated TryInto implementation.
 */
class TryIntoError<T> private constructor(
    /**
     * Original input value which failed to convert via the generated TryInto
     * implementation.
     */
    val input: T,
    private val variantNames: String,
    private val outputType: String,
) {
    companion object {
        fun <T> new(
            input: T,
            variantNames: String,
            outputType: String,
        ): TryIntoError<T> =
            TryIntoError(
                input = input,
                variantNames = variantNames,
                outputType = outputType,
            )
    }

    override fun toString(): String = "Only $variantNames can be converted to $outputType"

    override fun equals(other: Any?): Boolean =
        other is TryIntoError<*> &&
            input == other.input &&
            variantNames == other.variantNames &&
            outputType == other.outputType

    override fun hashCode(): Int {
        var result = input?.hashCode() ?: 0
        result = 31 * result + variantNames.hashCode()
        result = 31 * result + outputType.hashCode()
        return result
    }
}
