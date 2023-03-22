package com.exam.openapi.kkapi

import java.time.LocalDateTime

data class KkBlogSearchDto(
    val title: String,
    val contents: String,
    val url: String,
    val blogname: String,
    val thumbnail: String,
    val dateTime: LocalDateTime
)