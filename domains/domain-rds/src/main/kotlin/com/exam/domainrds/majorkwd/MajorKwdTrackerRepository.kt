package com.exam.domainrds.majorkwd

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface MajorKwdTrackerRepository : JpaRepository<MajorKwdTracker, Long> {
    fun findTop10ByOrderBySearchCntDesc(): List<MajorKwdTracker>
    fun findByKwdName(kwdName: String): MajorKwdTracker?

    @Transactional
    @Modifying
    @Query("update MajorKwdTracker m set m.searchCnt = m.searchCnt + 1 where m.kwdName = :kwdName")
    fun incrementSearchCnt(kwdName: String)

//    @Transactional
//    @Modifying
//    fun dirtySave(majorKwdTracker: MajorKwdTracker)
}