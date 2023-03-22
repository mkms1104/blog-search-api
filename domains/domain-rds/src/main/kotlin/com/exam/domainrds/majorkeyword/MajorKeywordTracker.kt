package com.exam.domainrds.majorkeyword

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(indexes = [Index(name = "keyword_index", columnList = "keyword")])
class MajorKeywordTracker(
    keyword: String,
    searchCnt: Long
) {
    @Id
    @Column(name = "major_keyword_tracker_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(unique = true, nullable = false)
    val keyword = keyword

    @Column(nullable = false)
    var searchCnt = searchCnt
        private set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as MajorKeywordTracker

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}