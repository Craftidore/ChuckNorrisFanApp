package com.craftidore.chucknorris.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [InternalJoke::class], version = 1)
abstract class InternalJokeDatabase: RoomDatabase() {

    abstract fun internalJokeDao(): InternalJokeDao
}