package com.github.asuslennikov.taskman.domain.di

import com.github.asuslennikov.taskman.domain.TaskRepository

interface DomainDependencies {
    fun getTaskRepository(): TaskRepository
}