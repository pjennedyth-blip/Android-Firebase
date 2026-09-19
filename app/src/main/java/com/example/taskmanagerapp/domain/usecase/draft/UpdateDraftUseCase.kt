package com.example.taskmanagerapp.domain.usecase.draft

import com.example.taskmanagerapp.domain.model.TaskDraft
import com.example.taskmanagerapp.domain.repository.DraftRepository
import javax.inject.Inject

class UpdateDraftUseCase @Inject constructor(
    private val repository: DraftRepository
) {
    suspend operator fun invoke(draft: TaskDraft): Result<Unit> {
        return repository.updateDraft(draft)
    }
}