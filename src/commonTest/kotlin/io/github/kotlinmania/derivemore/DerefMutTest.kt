// port-lint: tests deref_mut.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test

/**
 * Tests mirroring upstream deref_mut.rs.
 *
 * Upstream uses the DerefMut derive to generate DerefMut trait impls,
 * which require a manual Deref impl to already exist. The file is 501
 * lines — most is compile-time verification of derive expansion with various
 * generic bounds and lifetimes. There are no test functions with
 * runtime assertions in the upstream file.
 *
 * Kotlin has no derive macros, so there are no runtime invariants to port.
 */
class DerefMutTest {
    @Test
    fun derefMutIsCompileTimeOnlyInUpstream() {
        // Upstream tests/deref_mut.rs has no test functions — it is
        // entirely compile-time derive-macro expansion verification.
    }
}
