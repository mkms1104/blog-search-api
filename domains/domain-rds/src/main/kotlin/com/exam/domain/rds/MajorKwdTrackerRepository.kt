package com.exam.domain.rds

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MajorKwdTrackerRepository : JpaRepository<MajorKwdTracker, Long> {
    fun findTop10ByOrderBySearchCntDesc(): List<MajorKwdTracker>
    fun findByKwdName(kwdName: String): MajorKwdTracker?
}