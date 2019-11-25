package com.github.asuslennikov.taskman.data.di

import com.github.asuslennikov.taskman.domain.TaskRepository
import com.github.asuslennikov.taskman.domain.di.DomainDependencies

interface DataProvider : DomainDependencies {
    override fun getTaskRepository(): TaskRepository
}