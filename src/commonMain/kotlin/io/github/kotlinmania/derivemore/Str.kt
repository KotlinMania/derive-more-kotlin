// port-lint: source str.rs
package io.github.kotlinmania.derivemore

/**
 * Error of parsing an enum value from its string representation.
 */
class FromStrError private constructor(
    private val typeName: String,
) : Exception("Invalid `$typeName` string representation") {
    companion object {
        fun new(typeName: String): FromStrError = FromStrError(typeName)
    }

    override fun toString(): String = message ?: ""

    override fun equals(other: Any?): Boolean =
        other is FromStrError && typeName == other.typeName

    override fun hashCode(): Int = typeName.hashCode()
}
