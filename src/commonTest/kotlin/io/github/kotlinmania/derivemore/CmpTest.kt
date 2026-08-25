// port-lint: tests eq.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

class CmpTest {
    @Test
    fun assertParamIsEqExistsAsMarkerClass() {
        // AssertParamIsEq is a phantom-type marker used by generated code,
        // mirroring the upstream AssertParamIsEq type. It has no public
        // constructor because it exists only for type-level assertions.
        assertEquals("AssertParamIsEq", AssertParamIsEq::class.simpleName)
    }
}
