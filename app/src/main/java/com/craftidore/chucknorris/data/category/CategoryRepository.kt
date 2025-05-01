package com.craftidore.chucknorris.data.category

import com.craftidore.chucknorris.data.ChuckNorrisApiService

class CategoryRepository(private val chuckNorrisApiService: ChuckNorrisApiService) {
    private var cache: List<String>? = null

    fun hasCache(): Boolean {
        return cache != null
    }

    suspend fun getCategories(): List<String> {
        if (cache == null) {
            val apiCategories: List<String> = chuckNorrisApiService.getCategories()
            // These wouldn't be Harding appropriate,
            // and I personally wouldn't want them in an app I created anyway, so...
            cache = apiCategories.filter { it != "explicit" }
        }
        return cache!!
    }
}