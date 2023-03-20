package com.exam.api.kk

import org.junit.jupiter.api.Test
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.web.util.UriComponentsBuilder

internal class SearchApiTest {
    private val restTemplate = RestTemplateBuilder()
        .rootUri("https://dapi.kakao.com")
        .build()

    @Test
    fun aaa() {
        val uri = UriComponentsBuilder
            .fromUriString("/v2/search/blog")
            .queryParam("query", "청바지")
            .build().toUriString()

        val httpHeaders = HttpHeaders().apply {
            add("Authorization", "KakaoAK a5a7fc605b86230e75b1d803e7b5f39b")
        }

        val requestEntity = RequestEntity
            .method(HttpMethod.GET, uri)
            .headers(httpHeaders)
            .build()

        val result = restTemplate.exchange(requestEntity, String::class.java)
        println(result)
    }
}