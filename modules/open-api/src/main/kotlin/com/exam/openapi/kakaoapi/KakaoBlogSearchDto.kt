package com.exam.openapi.kakaoapi

import java.time.LocalDateTime

data class KakaoBlogSearchDto(
    val title: String,
    val contents: String,
    val url: String,
    val blogname: String,
    val thumbnail: String,
    val dateTime: LocalDateTime
)