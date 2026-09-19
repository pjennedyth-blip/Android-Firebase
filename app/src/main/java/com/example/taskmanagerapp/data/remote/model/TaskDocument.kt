package com.example.taskmanagerapp.data.remote.model

data class TaskDocument(
    val id: String = "",
    val ownerId: String = "",
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)