package com.exam.blogsearchapiapp.util

import org.springframework.data.domain.Pageable

object PageableUtil {
    data class SimplePageInfo(
        val page: Int,
        val size: Int,
        val sort: String
    )

    // return single sort property in pageable
    fun getSingleSortProperty(pageable: Pageable): String {
        return pageable.sort.get().findFirst().orElse(null)?.property ?: ""
    }

    // convert Pageable to SimplePageInfo
    fun toSimplePageInfo(pageable: Pageable): SimplePageInfo {
        return SimplePageInfo(pageable.pageNumber, pageable.pageSize, getSingleSortProperty(pageable))
    }
}