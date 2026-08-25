// port-lint: tests index.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test

/**
 * Tests mirroring upstream index.rs.
 *
 * Upstream uses the Index derive to generate Index trait impls for structs
 * wrapping a collection. The entire upstream test file is compile-time
 * verification — it only checks that the derive macro expands correctly with
 * no runtime assertions. Kotlin has no derive macros and no equivalent
 * compile-time code generation, so there are no runtime invariants to port.
 *
 * The upstream file defines MyVec and Numbers structs with the Index derive
 * and index field attribute. The generated code delegates indexing to the inner list.
 * In Kotlin, this would be a manual operator fun get(index: Int) — wrapper code
 * with no behavioral surprise to test.
 */
class IndexTest {
    @Test
    fun indexDeriveIsCompileTimeOnlyInUpstream() {
        // Upstream tests/index.rs has no test functions — it is entirely
        // compile-time derive-macro expansion verification.
    }
}
