package com.github.asuslennikov.taskman.data.di

import android.content.Context
import androidx.room.Room
import com.github.asuslennikov.taskman.data.TaskRepositoryImpl
import com.github.asuslennikov.taskman.data.database.TaskDao
import com.github.asuslennikov.taskman.data.database.TaskmanDatabase
import com.github.asuslennikov.taskman.domain.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [DataModule.DataModuleBindings::class])
object DataModule {

    @Provides
    @DataScope
    @JvmStatic
    fun database(context: Context): TaskmanDatabase {
        return Room.databaseBuilder(context, TaskmanDatabase::class.java, "taskman_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @DataScope
    @JvmStatic
    fun taskDao(database: TaskmanDatabase): TaskDao = database.taskDao

    @Module
    internal interface DataModuleBindings {

        @Binds
        @DataScope
        fun bindsTaskRepository(impl: TaskRepositoryImpl): TaskRepository
    }
}