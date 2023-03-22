package com.exam.openapi.nvapi

import com.exam.openapi.kkapi.KkSearchApi
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class NvSearchApiTest {
    @Test
    @DisplayName("required parameter check test")
    fun blog() {
        val nvSearchApi = spyk<KkSearchApi>()

        // Mocking to prevent API requests
//        every { nvSearchApi.execute(any(), any(), String::class.java) } answers { ResponseEntity.ok("{}") }

        // ============================ check kwdName param ============================ //
        assertThrows<IllegalStateException> { nvSearchApi.blog("") }.also {
            assertEquals("kwdName should not be null", it.message)
        }

        // ============================ check page param ============================ //
        assertThrows<IllegalStateException> { nvSearchApi.blog("청바지", -1, 10) }.also {
            assertEquals("page should be between 1 and 50", it.message)
        }
        assertThrows<IllegalStateException> { nvSearchApi.blog("청바지", 0, 10) }.also {
            assertEquals("page should be between 1 and 50", it.message)
        }
        assertThrows<IllegalStateException> { nvSearchApi.blog("청바지", 101, 10) }.also {
            assertEquals("page should be between 1 and 50", it.message)
        }

        // ============================ check size param ============================ //
        assertThrows<IllegalStateException> { nvSearchApi.blog("청바지", 1, -1) }.also {
            assertEquals("size should be between 1 and 50", it.message)
        }
        assertThrows<IllegalStateException> { nvSearchApi.blog("청바지", 1, 0) }.also {
            assertEquals("size should be between 1 and 50", it.message)
        }
        assertThrows<IllegalStateException> { nvSearchApi.blog("청바지", 1, 101) }.also {
            assertEquals("size should be between 1 and 50", it.message)
        }

        // ============================ check normal case ============================ //
        assertDoesNotThrow { nvSearchApi.blog("청바지", 1, 10) }
    }

}