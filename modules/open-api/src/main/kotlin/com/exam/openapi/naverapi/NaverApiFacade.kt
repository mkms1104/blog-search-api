package com.exam.openapi.naverapi

import org.springframework.stereotype.Component

@Component
class NaverApiFacade {
    val searchApi = NaverSearchApi()
    // write api instance
}