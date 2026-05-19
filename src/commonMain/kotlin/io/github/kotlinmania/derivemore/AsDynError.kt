// port-lint: source as_dyn_error.rs
package io.github.kotlinmania.derivemore

/**
 * Coercion to [Throwable] used by generated expansions.
 *
 * The initial idea and implementation came from the thiserror crate's
 * AsDynError support, then was adjusted for use in derive-more.
 *
 * The original code was dual licensed under Apache License, Version 2.0 and
 * MIT licenses.
 *
 * AsDynError: https://github.com/dtolnay/thiserror/blob/2.0.3/src/aserror.rs
 * thiserror: https://github.com/dtolnay/thiserror/blob/2.0.3
 * Apache License: https://github.com/dtolnay/thiserror/blob/2.0.3/LICENSE-APACHE
 * MIT License: https://github.com/dtolnay/thiserror/blob/2.0.3/LICENSE-MIT
 */
interface AsDynError : Sealed {
    fun deriveMoreAsDynError(): Throwable
}

fun Throwable.deriveMoreAsDynError(): Throwable = this

interface Sealed
