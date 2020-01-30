package com.github.asuslennikov.taskman.task.card

import com.github.asuslennikov.mvvm.presentation.AbstractViewModel
import javax.inject.Inject

class TaskCardViewModel @Inject constructor() : AbstractViewModel<TaskCardState>() {
    override fun buildInitialState(): TaskCardState = throw RuntimeException("Never build a state out of scratch. Id must be passed from outside")

    override fun mergeState(currentState: TaskCardState?, savedState: TaskCardState?): TaskCardState {
        return savedState ?: throw RuntimeException("Initial state with id should be provided for this viewModel")
    }
}