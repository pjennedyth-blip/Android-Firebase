package com.example.taskmanagerapp.domain.usecase.draft

import com.example.taskmanagerapp.domain.model.Task
import com.example.taskmanagerapp.domain.model.TaskDraft
import com.example.taskmanagerapp.domain.repository.DraftRepository
import com.example.taskmanagerapp.domain.repository.TaskRepository
import javax.inject.Inject

class PublishDraftUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val draftRepository: DraftRepository
) {
    suspend operator fun invoke(draft: TaskDraft): Result<Unit> {
        val task = Task(
            title = draft.title,
            description = draft.description,
            ownerId = draft.ownerId,
            completed = false,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        val remoteResult = taskRepository.createTask(task)
        return if (remoteResult.isSuccess) {
            draftRepository.deleteDraft(draft.id)
        } else {
            Result.failure(remoteResult.exceptionOrNull() ?: Exception("Unknown error publishing task"))
        }
    }
}