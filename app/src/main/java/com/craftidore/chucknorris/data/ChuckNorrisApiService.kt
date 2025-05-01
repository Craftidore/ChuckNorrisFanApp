package com.craftidore.chucknorris.data

import com.craftidore.chucknorris.data.joke.Joke
import com.craftidore.chucknorris.data.search.Search
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckNorrisApiService {
    @GET("random")
    suspend fun getJoke(@Query("category") category: String): Joke

    @GET("categories")
    suspend fun getCategories(): List<String>

    @GET("search")
    suspend fun getSearch(@Query("query") query: String): Search
}