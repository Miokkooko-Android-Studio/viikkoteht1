package com.example.viikkoteht1.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.util.copy
import com.example.viikkoteht1.model.Task
import com.example.viikkoteht1.viewmodel.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: TaskViewModel,
    onNavigateBack: () -> Unit = {}
){
    val uiState by viewModel.uiState.collectAsState()
    val taskList = uiState.todos


    val sortedTasks = taskList.sortedWith(
        compareBy<Task> { it.dueDate == null }
            .thenBy { it.dueDate }
    )


    val groupedTasks = sortedTasks.groupBy { it.dueDate ?: "No date" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo list") },
                actions = {
                    IconButton(onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column( modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                groupedTasks.forEach { (date, tasksForDate) ->


                    item {
                        Text(
                            text = date,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(tasksForDate) { task ->
                        calendarItem(todo = task)
                    }
                }
            }
            }
        }
    }


@Composable
fun calendarItem(
    todo: Task
){

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp))
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),


        ){

            Text(todo.title)
            if(todo.description.isNotBlank()) {
                Text(text = todo.description ,
                    style = MaterialTheme.typography.bodySmall,)
            }
        }
    }
    }
