package com.example.viikkoteht1.model



data class TodoUiState(
    val todos: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val newTodoTitle: String = "",
    val newTodoDescription: String = "",
    val filter: TodoFilter = TodoFilter.ALL
)

enum class TodoFilter {
    ALL, ACTIVE, COMPLETED
}