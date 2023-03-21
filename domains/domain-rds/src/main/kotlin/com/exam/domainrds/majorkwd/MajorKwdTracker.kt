package com.exam.domainrds.majorkwd

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
class MajorKwdTracker(
    kwdName: String,
    searchCnt: Long
) {
    @Id
    @Column(name = "major_kwd_tracker_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(unique = true, nullable = false)
    val kwdName = kwdName

    @Column(nullable = false)
    var searchCnt = searchCnt
        private set

    fun doSearch() {
        this.searchCnt += 1
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as MajorKwdTracker

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}