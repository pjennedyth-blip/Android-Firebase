package com.example.taskmanagerapp.data.mapper

import com.example.taskmanagerapp.data.local.entity.TaskDraftEntity
import com.example.taskmanagerapp.data.remote.model.TaskDocument
import com.example.taskmanagerapp.domain.model.Task
import com.example.taskmanagerapp.domain.model.TaskDraft

fun TaskDocument.toDomain(): Task {
    return Task(
        id = id,
        ownerId = ownerId,
        title = title,
        description = description,
        completed = completed,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Task.toDocument(): TaskDocument {
    return TaskDocument(
        id = id,
        ownerId = ownerId,
        title = title,
        description = description,
        completed = completed,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun TaskDraftEntity.toDomain(): TaskDraft {
    return TaskDraft(
        id = id,
        ownerId = ownerId,
        title = title,
        description = description,
        savedAt = savedAt
    )
}

fun TaskDraft.toEntity(): TaskDraftEntity {
    return TaskDraftEntity(
        id = id,
        ownerId = ownerId,
        title = title,
        description = description,
        savedAt = savedAt
    )
}