package com.example.taskmanagerapp.domain.repository

import com.example.taskmanagerapp.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(ownerId: String): Flow<List<Task>>
    suspend fun createTask(task: Task): Result<String>
    suspend fun updateTask(task: Task): Result<Unit>
    suspend fun deleteTask(taskId: String): Result<Unit>
}