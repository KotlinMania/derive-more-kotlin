// port-lint: source cmp.rs
package io.github.kotlinmania.derivemore

/**
 * Equality-assertion marker used by generated code.
 *
 * This is reimplemented here because the upstream marker it mirrors is not part of
 * a stable public API and could change at any time.
 */
class AssertParamIsEq<T> private constructor()
