package com.github.asuslennikov.taskman.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.asuslennikov.taskman.data.database.entity.TaskEntity

@Dao
interface TaskDao {
    @Insert
    fun insert(task: TaskEntity)

    @Update
    fun update(task: TaskEntity)

    @Query("SELECT * from task WHERE taskId = :id")
    fun getById(id: Long): TaskEntity?
}
