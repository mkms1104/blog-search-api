package com.exam.api.kk

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder

@Service
class SearchApi {
    private val restTemplate = RestTemplateBuilder()
        .rootUri("https://dapi.kakao.com")
        .build()

    fun search(kwdName: String, sort: String): JsonObject {
        val uri = UriComponentsBuilder
            .fromUriString("/v2/search/blog")
            .queryParam("query", kwdName)
            .queryParam("sort", sort)
            .build().toUriString()

        val httpHeaders = HttpHeaders().apply {
            add("Authorization", "KakaoAK a5a7fc605b86230e75b1d803e7b5f39b")
        }

        val requestEntity = RequestEntity
            .method(HttpMethod.GET, uri)
            .headers(httpHeaders)
            .build()

        val result = restTemplate.exchange(requestEntity, String::class.java)
        return JsonParser.parseString(result.body).asJsonObject
    }
}