package com.exam.openapi.kakaoapi

import io.mockk.spyk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class KakaoSearchApiTest {
    @Test
    @DisplayName("required parameter check test")
    fun blog() {
        val kkSearchApi = spyk<KakaoSearchApi>()

        // ============================ check kwdName param ============================ //
        assertThrows<IllegalStateException> { kkSearchApi.blog("") }.also {
            assertEquals("kwdName should not be null", it.message)
        }

        // ============================ check page param ============================ //
        assertThrows<IllegalStateException> { kkSearchApi.blog("청바지", -1, 10) }.also {
            assertEquals("page should be between 1 and 50", it.message)
        }
        assertThrows<IllegalStateException> { kkSearchApi.blog("청바지", 0, 10) }.also {
            assertEquals("page should be between 1 and 50", it.message)
        }
        assertThrows<IllegalStateException> { kkSearchApi.blog("청바지", 51, 10) }.also {
            assertEquals("page should be between 1 and 50", it.message)
        }

        // ============================ check size param ============================ //
        assertThrows<IllegalStateException> { kkSearchApi.blog("청바지", 1, -1) }.also {
            assertEquals("size should be between 1 and 50", it.message)
        }
        assertThrows<IllegalStateException> { kkSearchApi.blog("청바지", 1, 0) }.also {
            assertEquals("size should be between 1 and 50", it.message)
        }
        assertThrows<IllegalStateException> { kkSearchApi.blog("청바지", 1, 51) }.also {
            assertEquals("size should be between 1 and 50", it.message)
        }

        // ============================ check normal case ============================ //
        assertDoesNotThrow { kkSearchApi.blog("청바지", 1, 10) }
    }

}