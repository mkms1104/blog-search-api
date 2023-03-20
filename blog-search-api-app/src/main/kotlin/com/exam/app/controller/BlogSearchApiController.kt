package com.exam.app.controller

import com.exam.app.dto.BlogSearchApiReq
import com.exam.app.dto.BlogSearchApiRes
import com.exam.app.service.BlogSearchApiService
import com.exam.domain.rds.MajorKwdTrackerRepository
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