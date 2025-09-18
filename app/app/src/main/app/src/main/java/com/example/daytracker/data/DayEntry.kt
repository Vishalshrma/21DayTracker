package com.example.daytracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_entries")
data class DayEntry(
    @PrimaryKey val date: String, // format yyyy-MM-dd
    val warmWater: Boolean = false,
    val walk: Boolean = false,
    val mealsOnTime: Boolean = false,
    val chewedFood: Boolean = false,
    val noRawAfter3: Boolean = false,
    val dinnerBefore645: Boolean = false,
    val noJunk: Boolean = false,
    val hydration: Boolean = false,
    val noScreensBeforeBed: Boolean = false,
    val sleptEarly: Boolean = false,
    val note: String? = null
) {
    fun progressPercent(): Int {
        val fields = listOf(
            warmWater, walk, mealsOnTime, chewedFood, noRawAfter3,
            dinnerBefore645, noJunk, hydration, noScreensBeforeBed, sleptEarly
        )
        val done = fields.count { it }
        return if (fields.isEmpty()) 0 else (done * 100) / fields.size
    }
}
