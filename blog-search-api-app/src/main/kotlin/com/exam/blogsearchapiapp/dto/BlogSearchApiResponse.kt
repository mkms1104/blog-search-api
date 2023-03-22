package com.exam.blogsearchapiapp.dto

data class BlogSearchApiResponse(
    val title: String,
    val contents: String,
    val blogName: String,
    val url: String
)