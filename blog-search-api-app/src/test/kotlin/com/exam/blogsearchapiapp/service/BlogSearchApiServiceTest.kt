package com.exam.blogsearchapiapp.service

import com.exam.blogsearchapiapp.dto.BlogSearchApiRequest
import com.exam.domainrds.majorkwd.MajorKwdTrackerRepository
import com.exam.openapi.kkapi.KkApiFacade
import com.exam.openapi.nvapi.NvApiFacade
import com.ninjasquad.springmockk.SpykBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpServerErrorException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@SpringBootTest
class BlogSearchApiServiceTest {
    @Autowired
    private lateinit var blogSearchApiService: BlogSearchApiService

    @SpykBean
    private lateinit var kkApiFacade: KkApiFacade

    @SpykBean
    private lateinit var nvApiFacade: NvApiFacade

    @Autowired
    private lateinit var majorKwdTrackerRepository: MajorKwdTrackerRepository

    @BeforeEach
    fun reset() {
    }

    @AfterEach
    fun clear() {
//        majorKwdTrackerRepository.deleteAll()
    }

    @Test
    @DisplayName("유효하지 않은 sort 값이 넘어올 경우 IllegalArgumentException 예외가 발생한다.")
    fun throwIllegalArgumentExceptionWhenInvalidSort() {
        val invalidSort = "invalid sort value"
        val blogSearchApiRequest = BlogSearchApiRequest("컴퓨터")
        val pageable = PageRequest.of(1, 10, Sort.by("invalid sort value"))

        assertThrows(IllegalArgumentException::class.java) {
            blogSearchApiService.doSearch(
                blogSearchApiRequest,
                pageable
            )
        }.also {
            assertEquals("invalid sort value '${invalidSort}'", it.message)
        }
    }

    @Test
    @DisplayName("카카오 검색 API 호출 시 5xx 서버 예외가 발생할 경우, 네이버 검색 API 호출을 통해 재시도 한다.")
    fun doChainWhen5xxServerError() {
        // 카카오 검색 API 호출 시 HttpServerErrorException 발생하도록 stubbing
        every {
            kkApiFacade.searchApi.blog(any(), any(), any())
        } throws HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE)

        val blogSearchApiRequest = BlogSearchApiRequest("세탁기")
        val pageable = PageRequest.of(1, 10, Sort.by("ACCURACY"))

        // 검색 메서드 호출
        blogSearchApiService.doSearch(blogSearchApiRequest, pageable)

        // 네이버 검색 API 메서드 호출 확인
        verify(exactly = 1) { nvApiFacade.searchApi }
    }

    @ParameterizedTest
    @CsvSource(value = ["청바지,1", "청바지,2", "청바지,3", "운동화,1", "운동화,2", "운동화,3"])
    @DisplayName("싱글 쓰레드 환경에서 검색 API 호출 시 쿼리 별 검색 횟수가 정상 카운트된다.")
    fun doNormalCountWhenSingleThread(kwdName: String, searchCnt: Long) {
        val blogSearchApiRequest = BlogSearchApiRequest(kwdName)
        val pageable = PageRequest.of(1, 10)

        // 검색 메서드 호출
        blogSearchApiService.doSearch(blogSearchApiRequest, pageable)

        val majorKwdTracker = majorKwdTrackerRepository.findByKwdName(kwdName)!!
        assertEquals(searchCnt, majorKwdTracker.searchCnt)
    }

    @Test
    @DisplayName("멀티 쓰레드 환경에서 검색 API 호출 시 쿼리 별 검색 횟수가 정상 카운트된다.")
    fun doInvalidCountWhenMultiThread() {
        val kwdName01 = "노트북"
        val kwdName02 = "휴지통"
        val pageable = PageRequest.of(1, 10)

        val countDownLatch = CountDownLatch(100)
        val executor = Executors.newFixedThreadPool(10)
        for (i in 1..100) {
            executor.execute {
                blogSearchApiService.doSearch(BlogSearchApiRequest(kwdName01), pageable)
                blogSearchApiService.doSearch(BlogSearchApiRequest(kwdName02), pageable)
                countDownLatch.countDown()
            }
        }
        countDownLatch.await(10, TimeUnit.SECONDS)

        val majorKwdTracker01 = majorKwdTrackerRepository.findByKwdName(kwdName01)!!
        assertEquals(100, majorKwdTracker01.searchCnt)

        val majorKwdTracker02 = majorKwdTrackerRepository.findByKwdName(kwdName02)!!
        assertEquals(100, majorKwdTracker02.searchCnt)
    }
}