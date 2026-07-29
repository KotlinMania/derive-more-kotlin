// port-lint: tests tests/lib.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LibTest {
    @Test
    fun deriveNamesContainsExpectedDerives() {
        val names = DeriveMore.deriveNames
        assertTrue("From" in names)
        assertTrue("Into" in names)
        assertTrue("Add" in names)
        assertTrue("Display" in names)
        assertTrue("Error" in names)
        assertTrue("TryUnwrap" in names)
        assertEquals(27, names.size)
    }

    @Test
    fun helperModulesContainsExpectedModules() {
        val modules = DeriveMore.helperModules
        assertTrue("add" in modules)
        assertTrue("cmp" in modules)
        assertTrue("ops" in modules)
        assertTrue("as" in modules)
        assertTrue("fmt" in modules)
        assertTrue("as_dyn_error" in modules)
        assertTrue("str" in modules)
        assertTrue("convert" in modules)
        assertTrue("try_unwrap" in modules)
        assertEquals(9, modules.size)
    }

    @Test
    fun missingFeatureMessageIsNonEmpty() {
        assertTrue(DeriveMore.MISSING_FEATURE_MESSAGE.isNotEmpty())
    }

    @Test
    fun privateExportedHelpersContainsExpectedNames() {
        val helpers = DeriveMorePrivate.exportedHelpers
        assertTrue("Conv" in helpers)
        assertTrue("ExtractRef" in helpers)
        assertTrue("debugTuple" in helpers)
        assertTrue("DebugTuple" in helpers)
        assertTrue("AssertParamIsEq" in helpers)
        assertTrue("AsDynError" in helpers)
    }

    @Test
    fun deriveModuleMirrorsDeriveNames() {
        assertEquals(DeriveMore.deriveNames, Derive.definitions)
    }

    @Test
    fun withTraitModuleMirrorsDeriveNames() {
        assertEquals(DeriveMore.deriveNames, WithTrait.definitions)
    }
}
