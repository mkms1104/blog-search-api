package com.exam.openapi.nvapi

import org.junit.jupiter.api.Test
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.web.util.UriComponentsBuilder

internal class NvSearchApiTest {
    private val clientId = "Bl9hndTKcC_CnE1GO5po"
    private val clientSecret = "GWbZJNKiH5"

    private val restTemplate = RestTemplateBuilder()
        .rootUri("https://openapi.naver.com")
        .build()

    @Test
    fun aaa() {
        val uri = UriComponentsBuilder
            .fromUriString("/v1/search/blog.json")
            .queryParam("query", "청바지")
            .build().toUriString()

        val httpHeaders = HttpHeaders().apply {
            add("X-Naver-Client-Id", clientId)
            add("X-Naver-Client-Secret", clientSecret)
        }

        val requestEntity = RequestEntity
            .method(HttpMethod.GET, uri)
            .headers(httpHeaders)
            .build()

        val result = restTemplate.exchange(requestEntity, String::class.java)
        println(result)
    }
}