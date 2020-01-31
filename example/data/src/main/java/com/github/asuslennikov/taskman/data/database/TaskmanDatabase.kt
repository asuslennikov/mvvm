package com.github.asuslennikov.taskman.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.asuslennikov.taskman.data.database.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class TaskmanDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao

}