package com.example.taskmanagerapp.domain.usecase.task

import com.example.taskmanagerapp.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: String): Result<Unit> {
        return repository.deleteTask(taskId)
    }
}