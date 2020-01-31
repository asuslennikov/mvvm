package com.github.asuslennikov.taskman.task.list.item

import com.github.asuslennikov.taskman.task.list.recycler.ListItemTypeViewModel
import javax.inject.Inject

class TaskItemViewModel @Inject constructor() : ListItemTypeViewModel<TaskItemState>() {

    fun onTaskClick(screen: TaskItemScreen) {
        val taskItemState = getCurrentState(screen)
        sendEffect(screen, OpenTaskCardEffect(taskItemState.getId()))
    }
}