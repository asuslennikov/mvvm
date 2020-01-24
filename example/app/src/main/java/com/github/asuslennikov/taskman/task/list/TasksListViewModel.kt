package com.github.asuslennikov.taskman.task.list

import com.github.asuslennikov.mvvm.presentation.AbstractViewModel
import javax.inject.Inject

class TasksListViewModel @Inject constructor() : AbstractViewModel<TasksListState>() {

    override fun buildInitialState(): TasksListState {
        return TasksListState()
    }
}