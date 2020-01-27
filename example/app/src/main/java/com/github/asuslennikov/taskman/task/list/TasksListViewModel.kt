package com.github.asuslennikov.taskman.task.list

import com.github.asuslennikov.mvvm.presentation.AbstractViewModel
import com.github.asuslennikov.taskman.domain.task.GetTaskUseCase
import javax.inject.Inject

class TasksListViewModel @Inject constructor(private val getTaskUseCase: GetTaskUseCase) :
    AbstractViewModel<TasksListState>() {

    override fun buildInitialState(): TasksListState {
        return TasksListState()
    }

    fun onAddTaskClick() {

    }
}