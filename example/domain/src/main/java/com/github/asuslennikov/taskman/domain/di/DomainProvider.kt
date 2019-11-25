package com.github.asuslennikov.taskman.domain.di

import com.github.asuslennikov.taskman.domain.task.GetTaskUseCase

interface DomainProvider {
    fun getTaskUseCase(): GetTaskUseCase
}