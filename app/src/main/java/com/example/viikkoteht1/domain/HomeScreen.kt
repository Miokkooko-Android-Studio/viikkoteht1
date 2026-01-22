package com.example.viikkoteht1.domain

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel = viewModel()
) {
    val taskList = taskViewModel.tasks
    var text by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var text3 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Todo-list")
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(taskList) { task ->
                Column {
                    Text("${task.id} - ${task.title}: ${task.description}. ${task.done}. ${task.dueDate}")
                    Button(onClick = { taskViewModel.toggleDone(task.id) }) {
                        Text("Done")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Ener task") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = text2,
            onValueChange = { text2 = it },
            label = { Text("Enter description") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = text3,
            onValueChange = { newValue ->
                if (newValue.length <= 10 && newValue.all { it.isDigit() || it == '-' }) {
                    text3 = newValue
                }
            },
            label = { Text("Enter due date (YY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                taskViewModel.addTask(
                    Task(
                        id = taskList.size + 1,
                        title = text,
                        description = text2,
                        priority = taskList.size + 1,
                        dueDate = text3,
                        done = false
                    )
                )
                text = ""
                text2 = ""
                text3 = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { taskViewModel.sortByDueDate() }) {
                Text("Sort by date")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { taskViewModel.filterByDone(true) }) {
                Text("Sort by done")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { taskViewModel.filterByDone(false) }) {
                Text("Sort by not done")
            }
        }
    }
}