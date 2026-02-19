package com.example.viikkoteht1.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viikkoteht1.data.Task

import com.example.viikkoteht1.model.TodoUiState
import com.example.viikkoteht1.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.plus

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel(){

    val allTasks: StateFlow<List<Task>> = repository.allTasks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val pendingCount: StateFlow<Int> = repository.pendingTaskCount
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )



    fun addTask(title: String, description: String, dueDate:String) {

        viewModelScope.launch {
            val task = Task(
                title = title,
                dueDate = dueDate,
                description = description
            )
            repository.insert(task)

        }
    }

    fun toggleTask(task: Task)
    {
        viewModelScope.launch {
            val updated = task.copy(isCompleted = !task.isCompleted)
            repository.update(updated)
        }
    }

    fun editTask(task: Task)
    {
        viewModelScope.launch {
            val updated = task.copy(title = task.title, description = task.description)
            repository.update(updated)
        }
    }

    fun deleteTask(task:Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    fun deleteCompletedTasks() {
        viewModelScope.launch {
            deleteCompletedTasks()
        }
    }

}