package com.exam.blogsearchapiapp.controller

import com.exam.blogsearchapiapp.dto.BlogSearchApiRequest
import com.exam.blogsearchapiapp.dto.SuccessResponseDto
import com.exam.blogsearchapiapp.service.BlogSearchApiService
import com.exam.domainrds.majorkwd.MajorKwdTrackerRepository
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class BlogSearchApiController(
    private val blogSearchApiService: BlogSearchApiService,
    private val majorKwdTrackerRepository: MajorKwdTrackerRepository
) {
    // 블로그 검색
    @GetMapping("search/blog")
    fun search(
        blogSearchApiRequest: BlogSearchApiRequest,
        pageable: Pageable
    ): ResponseEntity<SuccessResponseDto> {
        return ResponseEntity.ok(
            SuccessResponseDto(
                blogSearchApiService.doSearch(blogSearchApiRequest, pageable),
                pageable
            )
        )
    }

    // 인기 검색어 목록
    @GetMapping("search/kwd")
    fun majorKwdList(): ResponseEntity<SuccessResponseDto> {
        return ResponseEntity.ok(SuccessResponseDto(majorKwdTrackerRepository.findTop10ByOrderBySearchCntDesc()))
    }
}