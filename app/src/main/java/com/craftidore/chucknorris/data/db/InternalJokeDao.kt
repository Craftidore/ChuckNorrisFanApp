package com.craftidore.chucknorris.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface InternalJokeDao {
    @Query("SELECT * FROM InternalJoke WHERE api_id = :id")
    fun getInternalJoke(id: Long): Flow<InternalJoke?>

    @Query("SELECT * FROM InternalJoke ORDER BY api_id")
    fun getInternalJokes(): Flow<List<InternalJoke>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addInternalJoke(internalJoke: InternalJoke)

    @Update
    fun updateInternalJoke(internalJoke: InternalJoke)

    @Delete
    fun deleteInternalJoke(internalJoke: InternalJoke)
}