package com.example.daytracker.data

class DayRepository(private val dao: DayDao) {

    suspend fun getDay(date: String) = dao.getDay(date)

    suspend fun upsert(day: DayEntry) = dao.upsert(day)

    suspend fun getAll() = dao.getAll()

    suspend fun clearAll() = dao.clearAll()
}
