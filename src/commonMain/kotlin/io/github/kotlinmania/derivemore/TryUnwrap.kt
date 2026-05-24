// port-lint: source try_unwrap.rs
package io.github.kotlinmania.derivemore

/**
 * Error returned by a generated try-unwrap implementation.
 */
class TryUnwrapError<T> private constructor(
    /**
     * Original input value which failed to convert via the generated
     * try-unwrap implementation.
     */
    val input: T,
    private val enumName: String,
    private val variantName: String,
    private val funcName: String,
) {
    companion object {
        fun <T> new(
            input: T,
            enumName: String,
            variantName: String,
            funcName: String,
        ): TryUnwrapError<T> =
            TryUnwrapError(
                input = input,
                enumName = enumName,
                variantName = variantName,
                funcName = funcName,
            )
    }

    override fun toString(): String =
        "Attempt to call `$enumName::$funcName()` on a `$enumName::$variantName` value"

    fun toException(): Exception = Exception(toString())

    override fun equals(other: Any?): Boolean =
        other is TryUnwrapError<*> &&
            input == other.input &&
            enumName == other.enumName &&
            variantName == other.variantName &&
            funcName == other.funcName

    override fun hashCode(): Int {
        var result = input?.hashCode() ?: 0
        result = 31 * result + enumName.hashCode()
        result = 31 * result + variantName.hashCode()
        result = 31 * result + funcName.hashCode()
        return result
    }
}
