package com.example.taskmanagerapp.data.repository

import com.example.taskmanagerapp.data.local.dao.TaskDraftDao
import com.example.taskmanagerapp.data.mapper.toDomain
import com.example.taskmanagerapp.data.mapper.toEntity
import com.example.taskmanagerapp.domain.model.TaskDraft
import com.example.taskmanagerapp.domain.repository.DraftRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.ArrayList
import javax.inject.Inject

class DraftRepositoryImpl @Inject constructor(
    private val taskDraftDao: TaskDraftDao
) : DraftRepository {

    override fun getDrafts(ownerId: String): Flow<List<TaskDraft>> {
        return taskDraftDao.getDrafts(ownerId).map { entityList ->
            val drafts = ArrayList<TaskDraft>()
            for (entity in entityList) {
                drafts.add(entity.toDomain())
            }
            drafts
        }
    }

    override suspend fun saveDraft(draft: TaskDraft): Result<Unit> {
        return try {
            taskDraftDao.insertDraft(draft.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateDraft(draft: TaskDraft): Result<Unit> {
        return try {
            taskDraftDao.updateDraft(draft.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteDraft(draftId: Int): Result<Unit> {
        return try {
            taskDraftDao.deleteDraftById(draftId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}