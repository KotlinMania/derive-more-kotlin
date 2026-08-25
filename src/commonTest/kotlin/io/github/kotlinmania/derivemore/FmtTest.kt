// port-lint: tests debug.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

class FmtTest {
    @Test
    fun debugTupleWithNoFields() {
        val result = debugTuple("Foo").finish()
        assertEquals("Foo", result)
    }

    @Test
    fun debugTupleWithSingleField() {
        val result =
            debugTuple("Foo")
                .field(10)
                .finish()
        assertEquals("Foo(10)", result)
    }

    @Test
    fun debugTupleWithMultipleFields() {
        val result =
            debugTuple("Foo")
                .field(10)
                .field("Hello World")
                .finish()
        assertEquals("Foo(10, \"Hello World\")", result)
    }

    @Test
    fun debugTupleWithSingleFieldAndEmptyName() {
        val result =
            debugTuple("")
                .field(10)
                .finish()
        assertEquals("(10,)", result)
    }

    @Test
    fun debugTupleFinishNonExhaustiveWithFields() {
        val result =
            debugTuple("Bar")
                .field(10)
                .finishNonExhaustive()
        assertEquals("Bar(10, ..)", result)
    }

    @Test
    fun debugTupleFinishNonExhaustiveWithoutFields() {
        val result =
            debugTuple("Bar")
                .finishNonExhaustive()
        assertEquals("Bar(..)", result)
    }

    @Test
    fun debugTupleWithNullField() {
        val result =
            debugTuple("Foo")
                .field(null)
                .finish()
        assertEquals("Foo(null)", result)
    }

    @Test
    fun debugTupleWithStringField() {
        val result =
            debugTuple("Foo")
                .field("hello")
                .finish()
        assertEquals("Foo(\"hello\")", result)
    }

    @Test
    fun debugTupleWithStringFieldWithEscapedQuotes() {
        val result =
            debugTuple("Foo")
                .field("he\"llo")
                .finish()
        assertEquals("Foo(\"he\\\"llo\")", result)
    }

    @Test
    fun debugTupleWithStringFieldWithBackslash() {
        val result =
            debugTuple("Foo")
                .field("he\\llo")
                .finish()
        assertEquals("Foo(\"he\\\\llo\")", result)
    }
}
