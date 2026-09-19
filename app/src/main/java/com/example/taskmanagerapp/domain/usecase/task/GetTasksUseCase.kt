package com.example.taskmanagerapp.domain.usecase.task

import com.example.taskmanagerapp.domain.model.Task
import com.example.taskmanagerapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(ownerId: String): Flow<List<Task>> {
        return repository.getTasks(ownerId)
    }
}