package com.github.asuslennikov.taskman.task.list.recycler

import com.github.asuslennikov.mvvm.api.presentation.State

interface ListItemState : State {
    fun getId(): Long
}
