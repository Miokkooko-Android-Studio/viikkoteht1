package com.example.viikkoteht1.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.viikkoteht1.model.Task
import com.example.viikkoteht1.model.TodoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.plus

class TaskViewModel : ViewModel(){

    private val _uiState=MutableStateFlow(TodoUiState())
    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()


    init{
        val todos= listOf(
            Task(1, "Buy milk", "Description 1", 1, "2023-10-31", false),
            Task(2, "Drink milk", "Description 2", 2, "2021-11-10", true),


        )

        _uiState.value= _uiState.value.copy(
            todos=todos
        )
    }

    fun addTask(task: Task) {

        _uiState.value = _uiState.value.copy(
            todos = _uiState.value.todos + task,
            newTodoTitle = "",
            newTodoDescription = ""
        )
    }

    fun updateTask(updatedTask:Task){
        _uiState.value = _uiState.value.copy(
            todos = _uiState.value.todos.map { task ->
                if (updatedTask.id == task.id) updatedTask else task
            }
        )
    }

    fun toggleDone(id: Int){
        _uiState.value = _uiState.value.copy(
            todos = _uiState.value.todos.map { todo ->
            if(todo.id == id)
            {
                todo.copy(done = !todo.done)
            }else {
                todo
            }
            }
        )
    }

    fun removeTask(id: Int){
        _uiState.value = _uiState.value.copy(
            todos = _uiState.value.todos.filter{it.id != id}
        )
    }

    fun filterByDone(done: Boolean)
    {
        var filteredValues = _uiState.value.copy(
            todos = _uiState.value.todos.filter{it.done == done}
        )

    }

    fun sortByDueDate(){
        _uiState.value = _uiState.value.copy(
            todos = _uiState.value.todos.sortedBy{it.dueDate}
        )
    }

    fun showAll(){

    }

}