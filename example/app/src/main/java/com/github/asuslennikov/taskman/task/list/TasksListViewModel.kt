package com.github.asuslennikov.taskman.task.list

import com.github.asuslennikov.mvvm.presentation.AbstractViewModel
import com.github.asuslennikov.taskman.domain.task.GetTaskUseCase
import com.github.asuslennikov.taskman.task.list.item.DateHeaderState
import com.github.asuslennikov.taskman.task.list.item.TaskItemState
import javax.inject.Inject

class TasksListViewModel @Inject constructor(private val getTaskUseCase: GetTaskUseCase) :
    AbstractViewModel<TasksListState>() {

    override fun buildInitialState(): TasksListState {
        return TasksListState(
            listOf(
                DateHeaderState(1L, "Today"),
                TaskItemState(1L, "Task 1", false),
                TaskItemState(2L, "Task 2", true),
                DateHeaderState(2L, "Yesterday"),
                TaskItemState(3L, "Task 15", true)
            )
        )
    }

    fun onAddTaskClick() {

    }
}