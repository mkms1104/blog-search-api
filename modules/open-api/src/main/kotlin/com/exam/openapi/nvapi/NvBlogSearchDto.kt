package com.exam.openapi.nvapi

data class NvBlogSearchDto(
    val title: String,
    val link: String,
    val description: String,
    val bloggername: String,
    val bloggerlink: String,
    val postdate: String,
)