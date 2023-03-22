package com.exam.blogsearchapiapp.service

import com.exam.blogsearchapiapp.dto.BlogSearchApiRequest
import com.exam.blogsearchapiapp.dto.BlogSearchApiResponse
import com.exam.domainrds.majorkeyword.MajorKeywordTracker
import com.exam.domainrds.majorkeyword.MajorKeywordTrackerRepository
import com.exam.openapi.kakaoapi.KakaoApiFacade
import com.exam.openapi.naverapi.NaverApiFacade
import com.google.common.cache.CacheBuilder
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BlogSearchApiService(
    private val majorKeywordTrackerRepository: MajorKeywordTrackerRepository,
    kakaoApiFacade: KakaoApiFacade,
    naverApiFacade: NaverApiFacade
) {
    // combine chain
    private val searchApiChain = KakaoSearch(kakaoApiFacade).apply { this.setNext(NaverSearch(naverApiFacade)) }

    // 적중률 우선순위에 따라 삭제되는 캐시 구현 필요
    private val kwdCache = CacheBuilder.newBuilder()
        .maximumSize(100000) // 해당 사이즈에 따라 조회 성능 차이 발생
        .recordStats()
        .build<String, String>()

    fun doSearch(request: BlogSearchApiRequest, pageable: Pageable): List<BlogSearchApiResponse> {
        val result = searchApiChain.search(request, pageable)
        val isFirstSave = synchronized(this) {
            // 신규 키워드 검색 시 최초 1회만 진입
            if (kwdCache.getIfPresent(request.keyword) == null) {
                // 단일 프로세스에서 문제 없으나 멀티 프로세스 환경에서 동시성 이슈 가능성 존재
                majorKeywordTrackerRepository.save(MajorKeywordTracker(request.keyword, 1))
                kwdCache.put(request.keyword, "")
                true
            } else false
        }
        if (!isFirstSave) majorKeywordTrackerRepository.incrementSearchCnt(request.keyword)
        return result
    }
}