// port-lint: source fmt.rs
package io.github.kotlinmania.derivemore

/**
 * Reimplementation of tuple-style debug output with a finishNonExhaustive()
 * method.
 */

/**
 * Same as a tuple debug builder, but with a finishNonExhaustive() method.
 *
 * Must eventually call finish() or finishNonExhaustive() on debug builders.
 */
class DebugTuple internal constructor(
    name: String,
) {
    private val output = StringBuilder(name)
    private var fields = 0
    private val emptyName = name.isEmpty()
    private var finished = false

    /**
     * Adds a new field to the generated tuple struct output.
     *
     * Example:
     *
     * A value shaped like Foo(10, "Hello World") can call field(10),
     * field("Hello World"), then finish() to produce
     * Foo(10, "Hello World").
     */
    fun field(value: Any?): DebugTuple {
        check(!finished) { "debug tuple output is already finished" }

        val prefix = if (fields == 0) "(" else ", "
        output.append(prefix)
        output.append(value.debugRepresentation())
        fields += 1
        return this
    }

    /**
     * Finishes output and returns any error encountered.
     *
     * Example:
     *
     * A value shaped like Foo(10, "Hello World") can call field(10),
     * field("Hello World"), then finish() to finish the tuple formatting.
     */
    fun finish(): String {
        if (!finished && fields > 0) {
            if (fields == 1 && emptyName && !isPretty()) {
                output.append(",")
            }
            output.append(")")
        }
        finished = true
        return output.toString()
    }

    /**
     * Marks the struct as non-exhaustive, indicating to the reader that there
     * are some other fields that are not shown in the debug representation, and
     * finishes output, returning any error encountered.
     *
     * Example:
     *
     * A value shaped like Bar(10, 1.0) can call field(10), then
     * finishNonExhaustive() to show that some other fields exist.
     */
    fun finishNonExhaustive(): String {
        if (!finished) {
            if (fields > 0) {
                output.append(", ..)")
            } else {
                output.append("(..)")
            }
        }
        finished = true
        return output.toString()
    }

    private fun isPretty(): Boolean = false
}

/**
 * Creates a new DebugTuple.
 */
fun debugTuple(name: String): DebugTuple = DebugTuple(name)

/**
 * Wrapper adding 4 spaces on newlines for inner pretty printed debug values.
 */
private class Padded(
    private val output: StringBuilder,
) {
    private var onNewline = true

    fun writeString(value: String) {
        val pieces = value.split("\n")
        for (index in pieces.indices) {
            if (onNewline) {
                output.append("    ")
            }
            output.append(pieces[index])
            if (index != pieces.lastIndex) {
                output.append('\n')
                onNewline = true
            } else {
                onNewline = false
            }
        }
    }
}

private fun Any?.debugRepresentation(): String =
    when (this) {
        null -> "null"
        is String -> "\"" + replace("\\", "\\\\").replace("\"", "\\\"") + "\""
        else -> toString()
    }
