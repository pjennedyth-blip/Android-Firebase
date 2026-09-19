package com.example.taskmanagerapp.domain.usecase.draft

import com.example.taskmanagerapp.domain.model.TaskDraft
import com.example.taskmanagerapp.domain.repository.DraftRepository
import javax.inject.Inject

class SaveDraftUseCase @Inject constructor(
    private val repository: DraftRepository
) {
    suspend operator fun invoke(draft: TaskDraft): Result<Unit> {
        return repository.saveDraft(draft)
    }
}