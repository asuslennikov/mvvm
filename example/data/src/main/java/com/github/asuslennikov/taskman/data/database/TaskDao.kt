package com.github.asuslennikov.taskman.data.database

import androidx.room.*
import com.github.asuslennikov.taskman.data.database.entity.TaskEntity
import io.reactivex.Observable

@Dao
interface TaskDao {
    @Insert
    fun insert(task: TaskEntity): Long

    @Update
    fun update(task: TaskEntity)

    @Query("SELECT * FROM task WHERE taskId = :id")
    fun getById(id: Long): TaskEntity?

    @Query("SELECT * FROM task ORDER BY timestamp DESC")
    fun getTasks(): Observable<List<TaskEntity>>

    @Delete
    fun delete(task: TaskEntity)
}
