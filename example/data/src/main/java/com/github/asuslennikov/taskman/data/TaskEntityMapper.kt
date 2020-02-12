package com.github.asuslennikov.taskman.data

import com.github.asuslennikov.taskman.data.database.entity.TaskEntity
import com.github.asuslennikov.taskman.domain.Task

class TaskEntityMapper {
    fun fromEntity(entity: TaskEntity) : Task =
        Task(
            entity.taskId,
            entity.title,
            entity.description,
            entity.date
        )
}