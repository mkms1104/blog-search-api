package com.exam.blogsearchapiapp.service

import com.exam.blogsearchapiapp.dto.BlogSearchApiRequest
import com.exam.blogsearchapiapp.dto.BlogSearchApiResponse
import org.springframework.data.domain.Pageable

// handler
interface BlogSearchApiChainHandler {
    fun search(request: BlogSearchApiRequest, pageable: Pageable): List<BlogSearchApiResponse> // handle request
    fun setNext(chain: BlogSearchApiChainHandler): BlogSearchApiChainHandler
}