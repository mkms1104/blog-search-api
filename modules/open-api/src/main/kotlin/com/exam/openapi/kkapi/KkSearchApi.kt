package com.exam.openapi.kkapi

import com.google.gson.Gson
import com.google.gson.JsonParser
import org.springframework.http.HttpMethod
import org.springframework.web.util.UriComponentsBuilder

class KkSearchApi : AbstractKkApi("https://dapi.kakao.com") {
    companion object {
        enum class SortType(desc: String) {
            ACCURACY("정확도순"), // default
            RECENCY("최신순")
            ;
        }
    }

    fun blog(
        kwdName: String,
        page: Int = 1,
        size: Int = 10,
        sort: SortType = SortType.ACCURACY
    ): List<KkBlogSearchDto> {
        check(kwdName.isNotBlank()) { "kwdName should not be null" }
        check(page in 1..50) { "page should be between 1 and 50" }
        check(size in 1..50) { "size should be between 1 and 50" }

        val uri = UriComponentsBuilder
            .fromUriString("/v2/search/blog")
            .queryParam("query", kwdName)
            .queryParam("sort", sort.name.lowercase())
            .queryParam("page", page)
            .queryParam("size", size)
            .build().toUri()

        val result = execute(uri, HttpMethod.GET, String::class.java)
        val jsonObject = JsonParser.parseString(result.body).asJsonObject
        val documents = jsonObject.getAsJsonArray("documents")
        return documents.map { Gson().fromJson(it.toString(), KkBlogSearchDto::class.java) }.toList()
    }

    // web, vclip, image, book, cafe...
}