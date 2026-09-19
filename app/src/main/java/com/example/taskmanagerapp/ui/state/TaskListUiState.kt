package com.example.taskmanagerapp.ui.state

import com.example.taskmanagerapp.domain.model.Task
import java.util.ArrayList

data class TaskListUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = ArrayList<Task>(),
    val searchQuery: String = "",
    val errorMessage: String? = null
)
