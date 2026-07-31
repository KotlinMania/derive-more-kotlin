// port-lint: tests tests/index_mut.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test

/**
 * Tests mirroring upstream `tests/index_mut.rs`.
 *
 * Upstream uses `#[derive(IndexMut)]` to generate `IndexMut` trait impls, which
 * requires a manual `Index` impl to already exist. The entire upstream test
 * file is compile-time verification — it only checks that the derive macro
 * expands correctly with no runtime assertions. Kotlin has no derive macros,
 * so there are no runtime invariants to port.
 */
class IndexMutTest {
    @Test
    fun indexMutDeriveIsCompileTimeOnlyInUpstream() {
        // Upstream tests/index_mut.rs has no #[test] functions — it is
        // entirely compile-time derive-macro expansion verification.
    }
}