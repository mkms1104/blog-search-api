package com.exam.openapi.kkapi

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.net.URI

abstract class AbstractKkApi(rootUri: String) {
    private val restTemplate: RestTemplate = RestTemplateBuilder()
        .rootUri(rootUri)
        .build()

    private val httpHeaders = HttpHeaders().apply {
        add("Authorization", "KakaoAK a5a7fc605b86230e75b1d803e7b5f39b")
    }

    protected open fun <T> execute(uri: URI, httpMethod: HttpMethod, clazz: Class<T>): ResponseEntity<T> {
        val requestEntity = RequestEntity
            .method(httpMethod, uri.toString())
            .headers(httpHeaders)
            .build()

        return restTemplate.exchange(requestEntity, clazz)
    }
}