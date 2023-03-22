package com.exam.blogsearchapiapp.service

import com.exam.blogsearchapiapp.dto.BlogSearchApiRequest
import com.exam.blogsearchapiapp.dto.BlogSearchApiResponse
import com.exam.blogsearchapiapp.util.PageableUtil
import com.exam.openapi.kakaoapi.KakaoApiFacade
import com.exam.openapi.kakaoapi.KakaoSearchApi.Companion.SortType
import org.springframework.data.domain.Pageable
import org.springframework.web.client.HttpServerErrorException

// concrete handler
class KakaoSearch(private val kakaoApiFacade: KakaoApiFacade) : BlogSearchApiChainHandler {
    private var nextHandler: BlogSearchApiChainHandler? = null

    override fun setNext(chain: BlogSearchApiChainHandler): BlogSearchApiChainHandler {
        nextHandler = chain
        return chain
    }

    override fun search(request: BlogSearchApiRequest, pageable: Pageable): List<BlogSearchApiResponse> {
        return try {
            val sortStr = PageableUtil.getSingleSortProperty(pageable).uppercase()
            val sort = if (sortStr.isBlank()) SortType.ACCURACY else SortType.valueOf(sortStr)

            val result = kakaoApiFacade.searchApi.blog(request.keyword, pageable.pageNumber, pageable.pageSize, sort)
            result.map { BlogSearchApiResponse(it.title, it.contents, it.blogname, it.url) }
        } catch (e: IllegalArgumentException) { // catch enum parse error
            throw IllegalArgumentException("invalid sort value '${PageableUtil.getSingleSortProperty(pageable)}'")
        } catch (e: HttpServerErrorException) { // catch api 5xx error
            nextHandler?.search(request, pageable) ?: throw IllegalStateException("next handler is null")
        }
    }
}