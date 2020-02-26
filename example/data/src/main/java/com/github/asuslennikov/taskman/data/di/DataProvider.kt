package com.github.asuslennikov.taskman.data.di

import com.github.asuslennikov.taskman.domain.TaskManager
import com.github.asuslennikov.taskman.domain.di.DomainDependencies

interface DataProvider : DomainDependencies {
    override fun getTaskManager(): TaskManager
}