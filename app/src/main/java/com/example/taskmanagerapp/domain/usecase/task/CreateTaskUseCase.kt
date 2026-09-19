package com.example.taskmanagerapp.domain.usecase.task

import com.example.taskmanagerapp.domain.model.Task
import com.example.taskmanagerapp.domain.repository.TaskRepository
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Result<String> {
        return repository.createTask(task)
    }
}