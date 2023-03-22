package com.exam.blogsearchapiapp.dto

import org.springframework.data.domain.Pageable

data class SuccessResponseDto(
    val data: Any,
    val pageInfo: Pageable? = null
)