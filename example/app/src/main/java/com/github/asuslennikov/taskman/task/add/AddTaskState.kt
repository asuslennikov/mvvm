package com.github.asuslennikov.taskman.task.add

import com.github.asuslennikov.mvvm.api.presentation.State

data class AddTaskState(
    val title: String,
    val description: String,
    val date: String
): State