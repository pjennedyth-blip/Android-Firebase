package com.example.taskmanagerapp.domain.usecase.draft

import com.example.taskmanagerapp.domain.repository.DraftRepository
import javax.inject.Inject

class DeleteDraftUseCase @Inject constructor(
    private val repository: DraftRepository
) {
    suspend operator fun invoke(draftId: Int): Result<Unit> {
        return repository.deleteDraft(draftId)
    }
}