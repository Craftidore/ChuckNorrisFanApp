package com.craftidore.chucknorris.data.joke

import com.craftidore.chucknorris.data.ChuckNorrisApiService

class JokeRepository(private val chuckNorrisApiService: ChuckNorrisApiService) {
    suspend fun getJoke(category: String): Joke {
        var joke: Joke = chuckNorrisApiService.getJoke(category)
        // Even if pulling from a category that isn't explicit,
        // I believe explicit jokes can still show up,
        // since jokes can be in multiple categories.
        while (joke.categories.contains("explicit")) {
            joke = chuckNorrisApiService.getJoke(category)
        }
        return joke
    }
}