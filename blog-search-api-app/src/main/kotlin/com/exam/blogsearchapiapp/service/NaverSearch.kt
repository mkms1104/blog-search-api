package com.exam.blogsearchapiapp.service

import com.exam.blogsearchapiapp.dto.BlogSearchApiRequest
import com.exam.blogsearchapiapp.dto.BlogSearchApiResponse
import com.exam.blogsearchapiapp.util.PageableUtil
import com.exam.openapi.naverapi.NaverApiFacade
import com.exam.openapi.naverapi.NaverSearchApi.Companion.SortType
import org.springframework.data.domain.Pageable
import org.springframework.web.client.HttpServerErrorException

// concrete handler
class NaverSearch(private val naverApiFacade: NaverApiFacade) : BlogSearchApiChainHandler {
    private var nextHandler: BlogSearchApiChainHandler? = null

    override fun setNext(chain: BlogSearchApiChainHandler): BlogSearchApiChainHandler {
        nextHandler = chain
        return chain
    }

    override fun search(request: BlogSearchApiRequest, pageable: Pageable): List<BlogSearchApiResponse> {
        return try {
            // sort parse adapter
            val sortStr = PageableUtil.getSingleSortProperty(pageable).uppercase()
            val sort = if (sortStr.isBlank()) SortType.SIM else {
                when (sortStr) {
                    "ACCURACY" -> SortType.SIM
                    "RECENCY" -> SortType.DATE
                    else -> throw IllegalArgumentException("invalid sort value '${sortStr}'")
                }
            }

            val result = naverApiFacade.searchApi.blog(request.keyword, pageable.pageNumber, pageable.pageSize, sort)
            result.map { BlogSearchApiResponse(it.title, it.description, it.bloggername, it.bloggerlink) }.toList()
        } catch (e: HttpServerErrorException) { // catch api 5xx error
            nextHandler?.search(request, pageable) ?: throw IllegalStateException("next handler is null")
        }
    }
}