package com.exam.blogsearchapiapp.controller

import com.exam.blogsearchapiapp.dto.BlogSearchApiRequest
import com.exam.blogsearchapiapp.dto.SuccessResponseDto
import com.exam.blogsearchapiapp.service.BlogSearchApiService
import com.exam.blogsearchapiapp.util.PageableUtil
import com.exam.domainrds.majorkeyword.MajorKeywordTrackerRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class BlogSearchApiController(
    private val blogSearchApiService: BlogSearchApiService,
    private val majorKeywordTrackerRepository: MajorKeywordTrackerRepository
) {
    // 블로그 검색
    @GetMapping("search/blog")
    fun search(
        blogSearchApiRequest: BlogSearchApiRequest,
        @PageableDefault(page = 1, size = 10) pageable: Pageable
    ): ResponseEntity<SuccessResponseDto> {
        return ResponseEntity.ok(
            SuccessResponseDto(
                blogSearchApiService.doSearch(blogSearchApiRequest, pageable),
                PageableUtil.toSimplePageInfo(pageable)
            )
        )
    }

    // 인기 검색어 목록
    @GetMapping("search/major-keywords")
    fun majorKeywords(): ResponseEntity<SuccessResponseDto> {
        return ResponseEntity.ok(SuccessResponseDto(majorKeywordTrackerRepository.findTop10ByOrderBySearchCntDesc()))
    }
}