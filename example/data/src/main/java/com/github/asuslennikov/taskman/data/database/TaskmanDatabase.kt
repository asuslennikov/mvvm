package com.github.asuslennikov.taskman.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.asuslennikov.taskman.data.database.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TaskmanDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao

}