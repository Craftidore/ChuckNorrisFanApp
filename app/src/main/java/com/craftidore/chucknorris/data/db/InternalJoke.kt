package com.craftidore.chucknorris.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.craftidore.chucknorris.data.joke.Joke

@Entity()
data class InternalJoke(
    @PrimaryKey
    @ColumnInfo(name = "api_id")
    var id: String = "",

    var joke: String = "",
)

fun JokeToInternalJoke(joke: Joke): InternalJoke {
    val txt: String = joke.value
    val id: String = joke.id
    return InternalJoke(id = id, joke = txt)
}