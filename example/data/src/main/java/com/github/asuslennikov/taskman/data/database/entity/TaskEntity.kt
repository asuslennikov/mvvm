package com.github.asuslennikov.taskman.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,
    @ColumnInfo(name = "title")
    val title: String = ""
)