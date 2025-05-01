package com.craftidore.chucknorris.data.search

import com.craftidore.chucknorris.data.ChuckNorrisApiService

class SearchRepository(val chuckNorrisApiService: ChuckNorrisApiService) {
    suspend fun getSearch(query: String): Search = chuckNorrisApiService.getSearch(query)
}