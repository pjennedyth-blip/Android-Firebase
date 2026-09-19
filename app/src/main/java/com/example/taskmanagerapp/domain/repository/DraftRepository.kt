package com.example.taskmanagerapp.domain.repository

import com.example.taskmanagerapp.domain.model.TaskDraft
import kotlinx.coroutines.flow.Flow

interface DraftRepository {
    fun getDrafts(ownerId: String): Flow<List<TaskDraft>>
    suspend fun saveDraft(draft: TaskDraft): Result<Unit>
    suspend fun updateDraft(draft: TaskDraft): Result<Unit>
    suspend fun deleteDraft(draftId: Int): Result<Unit>
}