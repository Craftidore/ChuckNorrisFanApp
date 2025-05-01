package com.craftidore.chucknorris.data.search

import com.craftidore.chucknorris.data.joke.Joke

data class Search (
    val total: Int,
    val result: List<Joke>
)