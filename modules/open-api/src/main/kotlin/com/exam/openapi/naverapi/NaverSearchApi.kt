package com.exam.openapi.naverapi

import com.google.gson.Gson
import com.google.gson.JsonParser
import org.springframework.http.HttpMethod
import org.springframework.web.util.UriComponentsBuilder

class NaverSearchApi : AbstractNaverApi("https://openapi.naver.com") {
    companion object {
        enum class SortType(desc: String) {
            SIM("정확도순"), // default
            DATE("최신순")
            ;
        }
    }

    fun blog(
        kwdName: String,
        page: Int = 1,
        size: Int = 10,
        sort: SortType = SortType.SIM
    ): List<NaverBlogSearchDto> {
        check(kwdName.isNotBlank()) { "kwdName should not be null" }
        check(page in 1..100) { "page should be between 1 and 100" }
        check(size in 1..100) { "size should be between 1 and 100" }

        val uri = UriComponentsBuilder
            .fromUriString("/v1/search/blog.json")
            .queryParam("query", kwdName)
            .queryParam("sort", sort.name.lowercase())
            .queryParam("start", page)
            .queryParam("display", size)
            .build().toUri()

        val result = execute(uri, HttpMethod.GET, String::class.java)
        val jsonObject = JsonParser.parseString(result.body).asJsonObject
        val items = jsonObject.getAsJsonArray("items")
        return items.map { Gson().fromJson(it.toString(), NaverBlogSearchDto::class.java) }.toList()
    }

    // news, shop, movie, image, doc...
}