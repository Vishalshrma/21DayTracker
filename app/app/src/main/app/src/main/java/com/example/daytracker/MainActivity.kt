package com.example.daytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.daytracker.data.AppDatabase
import com.example.daytracker.data.DayRepository
import com.example.daytracker.ui.DayViewModel
import com.example.daytracker.ui.TodayScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up database + repo
        val db = AppDatabase.getInstance(applicationContext)
        val repo = DayRepository(db.dayDao())

        setContent {
            // Create ViewModel with custom factory
            val viewModel: DayViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return DayViewModel(repo) as T
                    }
                }
            )
            TodayScreen(viewModel)
        }
    }
}

