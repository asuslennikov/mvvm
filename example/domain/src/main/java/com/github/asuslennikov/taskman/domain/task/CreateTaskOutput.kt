package com.github.asuslennikov.taskman.domain.task

import com.github.asuslennikov.mvvm.domain.AbstractUseCaseOutput
import com.github.asuslennikov.taskman.domain.Task

data class CreateTaskOutput(
    val task: Task
) : AbstractUseCaseOutput()