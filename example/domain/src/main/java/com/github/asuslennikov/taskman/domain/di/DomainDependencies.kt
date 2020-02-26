package com.github.asuslennikov.taskman.domain.di

import com.github.asuslennikov.taskman.domain.TaskManager

interface DomainDependencies {
    fun getTaskManager(): TaskManager
}