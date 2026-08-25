// port-lint: tests as_ref.rs
package io.github.kotlinmania.derivemore

import kotlin.test.Test
import kotlin.test.assertEquals

class AsTest {
    @Test
    fun identityExtractRefReturnsInput() {
        val ref = IdentityExtractRef<Int>()
        assertEquals(42, ref.deriveMoreExtractRef(42))
    }

    @Test
    fun asRefExtractRefDelegatesToAsRef() {
        class Wrapper(
            val inner: Int,
        ) : AsRef<Int> {
            override fun asRef(): Int = inner
        }

        val ref = AsRefExtractRef<Wrapper, Int>()
        val wrapper = Wrapper(42)
        assertEquals(42, ref.deriveMoreExtractRef(wrapper))
    }

    @Test
    fun mutableIdentityExtractRefReturnsInput() {
        val ref = MutableIdentityExtractRef<String>()
        assertEquals("hello", ref.deriveMoreExtractRef("hello"))
    }

    @Test
    fun asMutExtractRefDelegatesToAsMut() {
        class MutableWrapper(
            var inner: Int,
        ) : AsMut<Int> {
            override fun asMut(): Int = inner
        }

        val ref = AsMutExtractRef<MutableWrapper, Int>()
        val wrapper = MutableWrapper(42)
        assertEquals(42, ref.deriveMoreExtractRef(wrapper))
    }
}
