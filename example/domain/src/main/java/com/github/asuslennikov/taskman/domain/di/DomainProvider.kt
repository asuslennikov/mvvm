package com.github.asuslennikov.taskman.domain.di

import com.github.asuslennikov.taskman.domain.task.GetTasksUseCase

interface DomainProvider {
    fun getTasksUseCase(): GetTasksUseCase
}