package com.exam.openapi.nvapi

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.net.URI

abstract class AbstractNvApi(rootUri: String) {
    private val restTemplate: RestTemplate = RestTemplateBuilder()
        .rootUri(rootUri)
        .build()

    private val httpHeaders = HttpHeaders().apply {
        add("X-Naver-Client-Id", "Bl9hndTKcC_CnE1GO5po")
        add("X-Naver-Client-Secret", "GWbZJNKiH5")
    }

    protected fun <T> execute(uri: URI, httpMethod: HttpMethod, clazz: Class<T>): ResponseEntity<T> {
        val requestEntity = RequestEntity
            .method(httpMethod, uri.toString())
            .headers(httpHeaders)
            .build()

        return restTemplate.exchange(requestEntity, clazz)
    }
}