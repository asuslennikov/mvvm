package com.github.asuslennikov.taskman.task.card

import com.github.asuslennikov.mvvm.api.presentation.State

data class TaskCardState(val id: Long, val title: String, val description: String, val date: String) : State