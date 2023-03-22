package com.exam.blogsearchapiapp.dto

import com.exam.blogsearchapiapp.util.PageableUtil

data class SuccessResponseDto(
    val data: Any,
    val pageInfo: PageableUtil.SimplePageInfo? = null
)