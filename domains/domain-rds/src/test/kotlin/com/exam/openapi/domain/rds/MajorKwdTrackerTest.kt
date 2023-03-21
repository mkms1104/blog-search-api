package com.exam.openapi.domain.rds

import com.exam.domainrds.majorkwd.MajorKwdTrackerRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class MajorKwdTrackerTest {
    @Autowired
    private lateinit var majorKwdTrackerRepository: MajorKwdTrackerRepository

    @Test
    fun selectTop10() {
        majorKwdTrackerRepository.findTop10ByOrderBySearchCntDesc()
    }
}