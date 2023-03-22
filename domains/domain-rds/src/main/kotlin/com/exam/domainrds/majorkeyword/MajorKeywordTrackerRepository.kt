package com.exam.domainrds.majorkeyword

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface MajorKeywordTrackerRepository : JpaRepository<MajorKeywordTracker, Long> {
    fun findTop10ByOrderBySearchCntDesc(): List<MajorKeywordTracker>
    fun findByKeyword(kwdName: String): MajorKeywordTracker?

    @Transactional
    @Modifying
    @Query("update MajorKeywordTracker m set m.searchCnt = m.searchCnt + 1 where m.keyword = :keyword")
    fun incrementSearchCnt(keyword: String)
}