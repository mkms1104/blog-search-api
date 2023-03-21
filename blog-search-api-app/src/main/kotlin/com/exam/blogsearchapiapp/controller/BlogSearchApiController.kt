package com.exam.blogsearchapiapp.controller

import com.exam.domainrds.majorkwd.MajorKwdTrackerRepository
import com.exam.openapi.app.dto.BlogSearchApiReq
import com.exam.openapi.app.dto.BlogSearchApiRes
import com.exam.blogsearchapiapp.service.BlogSearchApiService
import com.google.gson.JsonObject
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
    fun aaa(blogSearchApiReq: BlogSearchApiReq): JsonObject {
        return blogSearchApiService.doSearch(blogSearchApiReq)
    }

    // 인기 검색어 목록
    @GetMapping("search/kwd")
    fun bbb(): List<BlogSearchApiRes> {
        return majorKwdTrackerRepository.findTop10ByOrderBySearchCntDesc()
            .map { BlogSearchApiRes(it.kwdName, it.searchCnt) }.toList()
    }
}