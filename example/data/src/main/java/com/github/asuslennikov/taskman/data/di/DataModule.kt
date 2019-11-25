package com.github.asuslennikov.taskman.data.di

import com.github.asuslennikov.taskman.data.TaskRepositoryImpl
import com.github.asuslennikov.taskman.domain.TaskRepository
import dagger.Binds
import dagger.Module

@Module
internal interface DataModule {

    @Binds
    @DataScope
    fun bindsTaskRepository(impl: TaskRepositoryImpl): TaskRepository

}