package com.exam.domainrds.majorkeyword

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class MajorKeywordTrackerTest {
    @Autowired
    private lateinit var majorKeywordTrackerRepository: MajorKeywordTrackerRepository

    @Test
    fun selectMajorKeywordTop10() {
        majorKeywordTrackerRepository.save(MajorKeywordTracker("청바지", 1))
        majorKeywordTrackerRepository.save(MajorKeywordTracker("운동화", 2))
        majorKeywordTrackerRepository.save(MajorKeywordTracker("니트", 3))
        majorKeywordTrackerRepository.save(MajorKeywordTracker("패딩", 4))
        majorKeywordTrackerRepository.save(MajorKeywordTracker("반팔", 5))
        majorKeywordTrackerRepository.save(MajorKeywordTracker("셔츠", 6))
        majorKeywordTrackerRepository.save(MajorKeywordTracker("구두", 7))
        majorKeywordTrackerRepository.save(MajorKeywordTracker("정장", 8))
        majorKeywordTrackerRepository.save(MajorKeywordTracker("모자", 9))
        majorKeywordTrackerRepository.save(MajorKeywordTracker("안경", 10))
        majorKeywordTrackerRepository.save(MajorKeywordTracker("슬렉스", 11))

        val majorKeywordTracker = majorKeywordTrackerRepository.findTop10ByOrderBySearchCntDesc()
        assertEquals(10, majorKeywordTracker.size)
        assertNull(majorKeywordTracker.find { it.keyword == "청바지" })
    }
}