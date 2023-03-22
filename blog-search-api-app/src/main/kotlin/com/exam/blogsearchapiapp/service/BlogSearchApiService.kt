package com.exam.blogsearchapiapp.service

import com.exam.blogsearchapiapp.dto.BlogSearchApiRequest
import com.exam.blogsearchapiapp.dto.BlogSearchApiResponse
import com.exam.domainrds.majorkwd.MajorKwdTracker
import com.exam.domainrds.majorkwd.MajorKwdTrackerRepository
import com.exam.openapi.kkapi.KkApiFacade
import com.exam.openapi.nvapi.NvApiFacade
import com.google.common.cache.CacheBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BlogSearchApiService(
    private val majorKwdTrackerRepository: MajorKwdTrackerRepository,
    kkApiFacade: KkApiFacade,
    nvApiFacade: NvApiFacade
) {
    // combine chain
    // 해당 작업이 복잡해질 경우 분리할 것.
    private val searchApiChain = KkSearch(kkApiFacade).apply { this.setNext(NvSearch(nvApiFacade)) }

    // 적중률 우선순위에 따라 삭제되는 캐시 구현 필요
    private val kwdCache = CacheBuilder.newBuilder()
        .maximumSize(100000) // 해당 사이즈에 따라 조회 성능 차이 발생
        .recordStats()
        .build<String, String>()

    // mutex lock
    private val mutex = Mutex()

    fun doSearch(request: BlogSearchApiRequest, pageable: Pageable): List<BlogSearchApiResponse> {
        val result = searchApiChain.search(request, pageable)

        runBlocking {
            // 단일 프로세스에서 문제 없으나 멀티 프로세스 환경에서 동시성 이슈 가능성 존재
            val isFirstSave = mutex.withLock {
                // 신규 키워드 검색 시 최초 1회만 진입
                if (kwdCache.getIfPresent(request.kwdName) == null) {
                    majorKwdTrackerRepository.save(MajorKwdTracker(request.kwdName, 1))
                    kwdCache.put(request.kwdName, "")
                    true
                } else false
            }

            if (!isFirstSave) withContext(Dispatchers.IO) {
                majorKwdTrackerRepository.incrementSearchCnt(request.kwdName)
            }
        }

        return result
    }
}