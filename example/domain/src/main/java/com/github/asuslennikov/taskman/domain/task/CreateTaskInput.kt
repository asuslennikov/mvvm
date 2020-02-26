package com.github.asuslennikov.taskman.domain.task

import com.github.asuslennikov.mvvm.api.domain.UseCaseInput
import com.github.asuslennikov.taskman.domain.Task

data class CreateTaskInput(val task: Task) : UseCaseInput