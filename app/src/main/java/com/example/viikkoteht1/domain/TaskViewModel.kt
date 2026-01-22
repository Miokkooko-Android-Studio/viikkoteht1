package com.example.viikkoteht1.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel(){
    var tasks by mutableStateOf(listOf<Task>())

    init{
        tasks= listOf(
        Task(1,"Task1", "Description 1", 1, "2023-10-31", false),
        Task(2,"Task2", "Description 2", 2, "2021-11-10", true),
        Task(3,"Task3", "Description 3", 3, "2026-11-11", false),
        Task(4,"Task4", "Description 4", 4, "2022-11-14", false)
        )
    }

    fun addTask(task: Task) {
        tasks += task
    }

    fun toggleDone(id: Int){
        tasks= tasks.map { task ->
            if (task.id == id) {
                task.copy(done = !task.done)
            } else {
                task
            }
        }
    }

    fun removeTask(id: Int){
        tasks = tasks.filter{it.id != id}
    }

    fun filterByDone(done: Boolean)
    {
        tasks= tasks.filter{it.done==done}
    }

    fun sortByDueDate(){
        tasks = tasks.sortedBy{it.dueDate}
    }
}