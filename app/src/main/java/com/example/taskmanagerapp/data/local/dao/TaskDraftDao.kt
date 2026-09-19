package com.example.taskmanagerapp.data.local.dao

import androidx.room.*
import com.example.taskmanagerapp.data.local.entity.TaskDraftEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDraftDao {
    @Query("SELECT * FROM task_drafts WHERE ownerId = :ownerId ORDER BY savedAt DESC")
    fun getDrafts(ownerId: String): Flow<List<TaskDraftEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraft(draft: TaskDraftEntity)

    @Update
    suspend fun updateDraft(draft: TaskDraftEntity)

    @Delete
    suspend fun deleteDraft(draft: TaskDraftEntity)

    @Query("DELETE FROM task_drafts WHERE id = :draftId")
    suspend fun deleteDraftById(draftId: Int)
}