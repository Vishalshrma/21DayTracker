package com.example.daytracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DayDao {
    @Query("SELECT * FROM day_entries WHERE date = :date LIMIT 1")
    suspend fun getDay(date: String): DayEntry?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(day: DayEntry)

    @Query("SELECT * FROM day_entries ORDER BY date ASC")
    suspend fun getAll(): List<DayEntry>

    @Query("DELETE FROM day_entries")
    suspend fun clearAll()
}
