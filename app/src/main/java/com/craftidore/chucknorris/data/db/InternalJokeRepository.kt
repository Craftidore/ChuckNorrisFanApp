package com.craftidore.chucknorris.data.db

import android.content.Context
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class InternalJokeRepository(context: Context) {
    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            CoroutineScope(Dispatchers.IO).launch {
                // Add Starter Data
            }
        }
    }

    private val database: InternalJokeDatabase = Room.databaseBuilder(
        context,
        InternalJokeDatabase::class.java,
        "joke.db"
    )
        .addCallback(databaseCallback)
        .build()

    private val internalJokeDao = database.internalJokeDao()

    fun getInternalJoke(id: Long) = internalJokeDao.getInternalJoke(id)

    fun getInternalJokes() = internalJokeDao.getInternalJokes()

    fun addInternalJoke(internalJoke: InternalJoke) = internalJokeDao.addInternalJoke(internalJoke)

    fun updateInternalJoke(internalJoke: InternalJoke) = internalJokeDao.updateInternalJoke(internalJoke)

    fun deleteInternalJoke(internalJoke: InternalJoke) = internalJokeDao.deleteInternalJoke(internalJoke)
}