package com.example.taskmanagerapp.ui.state

data class TaskFormUiState(
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false,
    val createdAt: Long = 0L,
    val operationState: OperationState = OperationState.Idle
)