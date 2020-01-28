package com.github.asuslennikov.taskman.task.list

import com.github.asuslennikov.mvvm.api.presentation.State
import com.github.asuslennikov.taskman.task.list.recycler.ListItemState

data class TasksListState(
    val tasks: List<ListItemState>
) : State