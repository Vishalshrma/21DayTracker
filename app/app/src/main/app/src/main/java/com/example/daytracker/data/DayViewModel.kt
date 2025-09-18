package com.example.daytracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daytracker.data.DayEntry
import com.example.daytracker.data.DayRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DayViewModel(private val repo: DayRepository) : ViewModel() {

    private val formatter = DateTimeFormatter.ISO_DATE
    private val _uiState = MutableStateFlow<DayEntry?>(null)
    val uiState: StateFlow<DayEntry?> = _uiState

    fun loadToday() {
        val today = LocalDate.now().format(formatter)
        viewModelScope.launch {
            val entry = repo.getDay(today) ?: DayEntry(date = today)
            _uiState.value = entry
        }
    }

    fun toggle(field: String) {
        val current = _uiState.value ?: return
        val updated = when (field) {
            "warmWater" -> current.copy(warmWater = !current.warmWater)
            "walk" -> current.copy(walk = !current.walk)
            "mealsOnTime" -> current.copy(mealsOnTime = !current.mealsOnTime)
            "chewedFood" -> current.copy(chewedFood = !current.chewedFood)
            "noRawAfter3" -> current.copy(noRawAfter3 = !current.noRawAfter3)
            "dinnerBefore645" -> current.copy(dinnerBefore645 = !current.dinnerBefore645)
            "noJunk" -> current.copy(noJunk = !current.noJunk)
            "hydration" -> current.copy(hydration = !current.hydration)
            "noScreensBeforeBed" -> current.copy(noScreensBeforeBed = !current.noScreensBeforeBed)
            "sleptEarly" -> current.copy(sleptEarly = !current.sleptEarly)
            else -> current
        }
        _uiState.value = updated
        viewModelScope.launch { repo.upsert(updated) }
    }

    fun saveNote(note: String) {
        val current = _uiState.value ?: return
        val updated = current.copy(note = note)
        _uiState.value = updated
        viewModelScope.launch { repo.upsert(updated) }
    }
}
