package com.exam.blogsearchapiapp.util

import org.springframework.data.domain.Pageable

object PageableUtil {
    // return single sort property in pageable
    fun getSingleSortProperty(pageable: Pageable): String {
        return pageable.sort.get().findFirst().orElse(null)?.property ?: ""
    }
}