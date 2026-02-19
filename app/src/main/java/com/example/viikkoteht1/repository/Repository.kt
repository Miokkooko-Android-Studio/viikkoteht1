package com.example.viikkoteht1.repository

// 📁 data/repository/TaskRepository.kt

import com.example.viikkoteht1.data.Task
import com.example.viikkoteht1.data.TaskDao
import kotlinx.coroutines.flow.Flow

// Repository ottaa DAO:n konstruktorin parametrina
// → Helppo testata: voidaan antaa mock-DAO testeissä
class TaskRepository(private val taskDao: TaskDao) {

    // Flow-tyyppiset propertyt: UI saa automaattisesti päivitykset
    // kun tietokanta muuttuu. Ei tarvitse erikseen hakea dataa uudelleen!
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    val pendingTaskCount: Flow<Int> = taskDao.getPendingTaskCount()

    // suspend = tämä funktio suoritetaan korutiinissa (taustasäikeessä)
    // Palauttaa lisätyn rivin id:n
    suspend fun insert(task: Task): Long {
        return taskDao.insert(task)
    }

    suspend fun update(task: Task) {
        taskDao.update(task)
    }

    suspend fun delete(task: Task) {
        taskDao.delete(task)
    }

    // Yksinkertainen toggle: päivitä vain is_completed-sarake
    suspend fun toggleTaskStatus(taskId: Int, completed: Boolean) {
        taskDao.updateTaskStatus(taskId, completed)
    }

    fun getTasksByStatus(completed: Boolean): Flow<List<Task>> {
        return taskDao.getTasksByStatus(completed)
    }

    fun searchTasks(query: String): Flow<List<Task>> {
        return taskDao.searchTasks(query)
    }

    suspend fun deleteCompletedTasks() {
        taskDao.deleteCompletedTasks()
    }
}