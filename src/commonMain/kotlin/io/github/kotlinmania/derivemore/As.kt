// port-lint: source as.rs
package io.github.kotlinmania.derivemore

/**
 * Type glue for autoref-based specialization, used in AsRef and AsMut macro
 * expansion.
 *
 * Allows tp specialize the direct self-reference case over the default
 * outer-wrapper reference case.
 *
 * Reference:
 * https://lukaskalbertodt.github.io/2019/12/05/generalized-autoref-based-specialization.html
 */

/**
 * Container to specialize over.
 */
class Conv<Frm, To> private constructor() {
    companion object {
        fun <Frm, To> default(): Conv<Frm, To> = Conv()
    }
}

/**
 * Trait performing the specialization.
 */
interface ExtractRef<Frm, To> {
    /**
     * Extracts the output type from the input one.
     */
    fun deriveMoreExtractRef(frm: Frm): To
}

class IdentityExtractRef<T> : ExtractRef<T, T> {
    override fun deriveMoreExtractRef(frm: T): T = frm
}

interface AsRef<To> {
    fun asRef(): To
}

class AsRefExtractRef<Frm, To> : ExtractRef<Frm, To>
    where Frm : AsRef<To> {
    override fun deriveMoreExtractRef(frm: Frm): To = frm.asRef()
}

class MutableIdentityExtractRef<T> : ExtractRef<T, T> {
    override fun deriveMoreExtractRef(frm: T): T = frm
}

interface AsMut<To> {
    fun asMut(): To
}

class AsMutExtractRef<Frm, To> : ExtractRef<Frm, To>
    where Frm : AsMut<To> {
    override fun deriveMoreExtractRef(frm: Frm): To = frm.asMut()
}
