package com.exam.openapi.naverapi

data class NaverBlogSearchDto(
    val title: String,
    val link: String,
    val description: String,
    val bloggername: String,
    val bloggerlink: String,
    val postdate: String,
)