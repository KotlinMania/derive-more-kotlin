// port-lint: source lib.rs
package io.github.kotlinmania.derivemore

/**
 * Crate-root wiring for the derive-more Kotlin port.
 *
 * The upstream Rust crate uses crate-level documentation links for derive
 * macros such as From, Into, FromStr, TryFrom, TryInto, IntoIterator, AsRef,
 * AsMut, Debug, Display-like traits, Error, Index, Deref, Not-like traits,
 * Add-like traits, Mul-like traits, Sum-like traits, mutable index and deref
 * traits, assignment traits, Eq, PartialEq, Constructor, IsVariant, Unwrap,
 * and TryUnwrap.
 *
 * The Rust root also owns feature gating, internal macro-expansion helpers,
 * and derive re-exports. Kotlin does not have the same derive-macro export
 * surface, so this file keeps the crate-root catalogue and helper routing
 * without creating central type aliases.
 */
object DeriveMore {
    val deriveNames: Set<String> =
        linkedSetOf(
            "From",
            "Into",
            "FromStr",
            "TryFrom",
            "TryInto",
            "IntoIterator",
            "AsRef",
            "AsMut",
            "Debug",
            "Display",
            "Error",
            "Index",
            "Deref",
            "Not",
            "Add",
            "Mul",
            "Sum",
            "IndexMut",
            "DerefMut",
            "AddAssign",
            "MulAssign",
            "Eq",
            "PartialEq",
            "Constructor",
            "IsVariant",
            "Unwrap",
            "TryUnwrap",
        )

    val helperModules: Set<String> =
        linkedSetOf(
            "add",
            "cmp",
            "ops",
            "as",
            "fmt",
            "as_dyn_error",
            "str",
            "convert",
            "try_unwrap",
        )

    const val MISSING_FEATURE_MESSAGE: String =
        "at least one derive feature must be enabled (or the \"full\" feature enabling all the derives)"
}

/**
 * Not public, but exported API for macro expansion internals only.
 */
object DeriveMorePrivate {
    val exportedHelpers: Set<String> =
        linkedSetOf(
            "Conv",
            "ExtractRef",
            "debugTuple",
            "DebugTuple",
            "AssertParamIsEq",
            "AsDynError",
        )
}

/**
 * Module containing derive definitions only, without their corresponding
 * traits.
 *
 * Use it in import paths when traits should stay out of scope and only derives
 * should be named.
 */
object Derive {
    val definitions: Set<String> = DeriveMore.deriveNames
}

/**
 * Module containing derive definitions with their corresponding traits along.
 *
 * Use it in import paths when derives should be named along with their traits.
 */
object WithTrait {
    val definitions: Set<String> = DeriveMore.deriveNames
}
