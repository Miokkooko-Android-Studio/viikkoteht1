package com.example.viikkoteht1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.viikkoteht1.domain.Task
import com.example.viikkoteht1.ui.theme.Viikkoteht1Theme
import com.example.viikkoteht1.domain.mockTasks
import com.example.viikkoteht1.domain.toggleDone
import com.example.viikkoteht1.domain.sortByDueDate
import com.example.viikkoteht1.domain.filterByDone
import com.example.viikkoteht1.domain.addTask

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Viikkoteht1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   HomeScreen(

                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen( modifier: Modifier = Modifier) {
    var taskList by remember {mutableStateOf(mockTasks)}
    var wholeTaskList by remember {mutableStateOf(mockTasks)}
    var text by remember {mutableStateOf("")}
    var text2 by remember {mutableStateOf("")}
    var text3 by remember {mutableStateOf("")}
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        Spacer(modifier = Modifier.height(height = 16.dp))
        Text("Todo-list")
        Spacer(modifier = Modifier.height(height = 16.dp))

        taskList.forEach { task ->
            Text("${task.id} -${task.title}: ${task.description}. ${task.done}. ${task.dueDate}")
            Button(onClick = { wholeTaskList= toggleDone(taskList,task.id)
                taskList=wholeTaskList}) {
                Text("Done")
                Spacer(modifier = Modifier.height(height = 16.dp))
            }
        }
        Spacer(modifier = Modifier.height(height = 16.dp))
        Column{
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter task") }
        )
            TextField(
                value = text2,
                onValueChange = { text2 = it },
                label = { Text("Enter description") }
            )
            TextField(
                value = text3,
                onValueChange = { newValue ->
                    if ( newValue.length <= 10 &&
                        newValue.all { it.isDigit() || it == '-'  }) {
                        text3 = newValue
                    }
                },
                label = { Text("Enter due date (YY-MM-DD)") }
            )

            Button(onClick = { wholeTaskList= addTask(taskList, Task(
                id = taskList.size + 1,
                title = text,
                description = text2,
                priority = taskList.size + 1,
                dueDate = text3,
                done = false,
            )
            )
             taskList=wholeTaskList}) {
                Text("Add task")
            }

    }
        Spacer(modifier = Modifier.height(height = 16.dp))
        Row{
            Button(onClick = { taskList= sortByDueDate(taskList)}) {
                Text("Sort by date")
            }
        Button(onClick = { taskList = wholeTaskList
            taskList= filterByDone(taskList, true)}) {
            Text("Sort by done")
        }
        Button(onClick = { taskList = wholeTaskList
            taskList= filterByDone(taskList, false)}) {
            Text("Sort by not done")
        }
    }
        Button(onClick = {
            taskList = wholeTaskList
        }) {
            Text("Show all")
        }
    }
}

@Composable
fun TextField() {
    TODO("Not yet implemented")
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Viikkoteht1Theme {
        HomeScreen()
    }
}