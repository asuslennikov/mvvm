package com.github.asuslennikov.taskman.task.list.item

import com.github.asuslennikov.mvvm.api.domain.UseCaseOutput
import com.github.asuslennikov.taskman.domain.Task
import com.github.asuslennikov.taskman.domain.task.*
import com.github.asuslennikov.taskman.task.list.recycler.ListItemTypeViewModel
import javax.inject.Inject

class TaskItemViewModel @Inject constructor(
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ListItemTypeViewModel<TaskItemState>() {

    fun onTaskClick(screen: TaskItemScreen) {
        val taskItemState = getCurrentState(screen)
        sendEffect(screen, OpenTaskCardEffect(taskItemState.getId()))
    }

    fun onTaskLongClick(screen: TaskItemScreen) {
        val taskItemState = getCurrentState(screen)
        collectDisposable(getTaskByIdUseCase.execute(GetTaskByIdInput(taskItemState.getId()))
            .subscribe(
                { result ->
                    if (UseCaseOutput.Status.SUCCESS == result.status && result.found) {
                        result.task?.run {
                            collectDisposable(
                                updateTaskUseCase.execute(UpdateTaskInput(Task(taskId, title, description, date, !completed)))
                                    .subscribe()
                            )
                        }
                    }
                },
                { error ->
                    throw RuntimeException("Unexpected GetTaskByIdUseCase execution error", error)
                }
            )
        )
    }

    fun onTaskSwiped(screen: TaskItemScreen) {
        val taskItemState = getCurrentState(screen)
        deleteTaskUseCase.execute(DeleteTaskInput(taskItemState.getId())).subscribe()
    }
}