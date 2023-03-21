package com.exam.blogsearchapiapp.service

import com.exam.domainrds.majorkwd.MajorKwdTracker
import com.exam.domainrds.majorkwd.MajorKwdTrackerRepository
import com.exam.openapi.app.dto.BlogSearchApiReq
import com.exam.openapi.kkapi.KkSearchApi
import com.google.gson.JsonObject
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.validation.ValidationException

@Service
class BlogSearchApiService(
    private val majorKwdTrackerRepository: MajorKwdTrackerRepository,
    private val kkSearchApi: KkSearchApi
) {
    private val sortMethods = setOf("accuracy", "recency")

    @Transactional
    fun doSearch(blogSearchApiReq: BlogSearchApiReq): JsonObject {
        if (blogSearchApiReq.kwdName.isBlank()) throw ValidationException("키워드 입력해")
        if (!sortMethods.contains(blogSearchApiReq.sort)) throw ValidationException("정렬 입력해")

        val kwdName = blogSearchApiReq.kwdName
        val sort = blogSearchApiReq.sort

        val majorKwdTracker = majorKwdTrackerRepository.findByKwdName(kwdName) ?: MajorKwdTracker(kwdName, 0)
        majorKwdTracker.doSearch() // searchCnt 증가
        majorKwdTrackerRepository.save(majorKwdTracker)

        return kkSearchApi.search(kwdName, sort)
    }
}