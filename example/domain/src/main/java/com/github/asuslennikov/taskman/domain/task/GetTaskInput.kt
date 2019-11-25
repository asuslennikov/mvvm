package com.github.asuslennikov.taskman.domain.task

import com.github.asuslennikov.mvvm.api.domain.UseCaseInput

data class GetTaskInput(val id: Long) : UseCaseInput {
}