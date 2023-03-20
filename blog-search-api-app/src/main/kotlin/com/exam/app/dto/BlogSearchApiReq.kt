package com.exam.app.dto

data class BlogSearchApiReq(
    val kwdName: String,
    val sort: String = "accuracy"
)