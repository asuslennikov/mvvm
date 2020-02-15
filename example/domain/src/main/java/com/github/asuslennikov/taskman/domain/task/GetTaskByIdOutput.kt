package com.github.asuslennikov.taskman.domain.task

import com.github.asuslennikov.mvvm.domain.AbstractUseCaseOutput
import com.github.asuslennikov.taskman.domain.Task

data class GetTaskByIdOutput(
    val task: Task?,
    val found: Boolean
) : AbstractUseCaseOutput()