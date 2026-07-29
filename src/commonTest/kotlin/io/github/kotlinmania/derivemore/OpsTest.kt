// port-lint: tests tests/add.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

class OpsTest {
    @Test
    fun unitErrorCarriesOperationNameAndDisplayMessage() {
        val error = UnitError.new("add")
        assertEquals("Cannot add() unit variants", error.toString())
        assertEquals("Cannot add() unit variants", error.message)
    }
}
