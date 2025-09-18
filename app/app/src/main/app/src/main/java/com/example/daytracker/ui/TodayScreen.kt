package com.example.daytracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun TodayScreen(viewModel: DayViewModel) {
    val entryState by viewModel.uiState.collectAsState()
    val entry = entryState
    val scope = rememberCoroutineScope()
    // Load today's entry when this screen appears
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        viewModel.loadToday()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            Text(text = "21-Day Tracker", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(12.dp))

            val progress = (entry?.progressPercent() ?: 0) / 100f
            LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))

            // If entry is null show a helper text
            if (entry == null) {
                Text("Loading...")
                return@Column
            }

            ChecklistItem("Drink 2–3 glasses warm water", entry.warmWater) { viewModel.toggle("warmWater") }
            ChecklistItem("45–60 min morning walk", entry.walk) { viewModel.toggle("walk") }
            ChecklistItem("Meals on time / IF", entry.mealsOnTime) { viewModel.toggle("mealsOnTime") }
            ChecklistItem("Chew food 45–60 times", entry.chewedFood) { viewModel.toggle("chewedFood") }
            ChecklistItem("No raw food after 3pm", entry.noRawAfter3) { viewModel.toggle("noRawAfter3") }
            ChecklistItem("Dinner finished by 6:45pm", entry.dinnerBefore645) { viewModel.toggle("dinnerBefore645") }
            ChecklistItem("No processed/junk", entry.noJunk) { viewModel.toggle("noJunk") }
            ChecklistItem("Hydration 2L+", entry.hydration) { viewModel.toggle("hydration") }
            ChecklistItem("No screens 90m before bed", entry.noScreensBeforeBed) { viewModel.toggle("noScreensBeforeBed") }
            ChecklistItem("Sleep early (7-8h)", entry.sleptEarly) { viewModel.toggle("sleptEarly") }

            Spacer(modifier = Modifier.height(12.dp))

            var noteState by remember { mutableStateOf(TextFieldValue(entry.note ?: "")) }
            OutlinedTextField(
                value = noteState,
                onValueChange = { noteState = it },
                label = { Text("Daily note / feelings") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                scope.launch { viewModel.saveNote(noteState.text) }
            }, modifier = Modifier.align(Alignment.End)) {
                Text("Save Note")
            }
        }
    }
}

@Composable
fun ChecklistItem(text: String, checked: Boolean, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = { onToggle() })
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun TodayScreenPreview() {
    // A dumb preview — uses no real ViewModel
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Preview: 21-Day Tracker")
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(progress = 0.4f, modifier = Modifier.fillMaxWidth())
    }
}
