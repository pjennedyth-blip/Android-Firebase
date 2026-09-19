package com.example.taskmanagerapp.domain.usecase.draft

import com.example.taskmanagerapp.domain.model.TaskDraft
import com.example.taskmanagerapp.domain.repository.DraftRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDraftsUseCase @Inject constructor(
    private val repository: DraftRepository
) {
    operator fun invoke(ownerId: String): Flow<List<TaskDraft>> {
        return repository.getDrafts(ownerId)
    }
}