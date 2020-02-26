package com.github.asuslennikov.taskman.domain.task

import com.github.asuslennikov.mvvm.api.domain.UseCaseInput
import com.github.asuslennikov.taskman.domain.Task

data class UpdateTaskInput(val task: Task) : UseCaseInput