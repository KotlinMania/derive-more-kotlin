// port-lint: tests tests/no_std.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test

/**
 * Tests mirroring upstream `tests/no_std.rs`.
 *
 * The upstream file is entirely compile-time verification — it uses
 * `#[derive(...)]` on several structs/enums with various trait combinations
 * and verifies the macros expand correctly in a no_std environment. The only
 * runtime test (`#[test] fn assert()`) is behind `#[rustversion::nightly]` and
 * tests `Error::source()` chaining on nightly-only error derive types.
 *
 * Kotlin has no derive macros, no no_std concept, and no nightly-only Error
 * source chain. There are no portable runtime invariants.
 */
class NoStdTest {
    @Test
    fun noStdIsCompileTimeOnlyInUpstream() {
        // Upstream tests/no_std.rs runtime test is nightly-only and tests
        // Error::source() chaining — a Rust-specific semantic with no
        // Kotlin equivalent.
    }
}