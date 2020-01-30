package com.github.asuslennikov.taskman.task.list.item

import com.github.asuslennikov.taskman.task.list.recycler.ListItemViewModel
import javax.inject.Inject

class TaskItemViewModel @Inject constructor() : ListItemViewModel<TaskItemState>() {

    fun onTaskClick(screen: TaskItemScreen) {
        val taskItemState = getCurrentState(screen)
        sendEffect(screen, OpenTaskCardEffect(taskItemState.getId()))
    }
}