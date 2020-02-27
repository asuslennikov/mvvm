package com.github.asuslennikov.taskman.task.list.item

import com.github.asuslennikov.taskman.domain.task.DeleteTaskInput
import com.github.asuslennikov.taskman.domain.task.DeleteTaskUseCase
import com.github.asuslennikov.taskman.task.list.recycler.ListItemTypeViewModel
import javax.inject.Inject

class TaskItemViewModel @Inject constructor(
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ListItemTypeViewModel<TaskItemState>() {

    fun onTaskClick(screen: TaskItemScreen) {
        val taskItemState = getCurrentState(screen)
        sendEffect(screen, OpenTaskCardEffect(taskItemState.getId()))
    }

    fun onTaskLongClick(screen: TaskItemScreen) {
        val taskItemState = getCurrentState(screen)
        deleteTaskUseCase.execute(DeleteTaskInput(taskItemState.getId())).subscribe()
    }
}