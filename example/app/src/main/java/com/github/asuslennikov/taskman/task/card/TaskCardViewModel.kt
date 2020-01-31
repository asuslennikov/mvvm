package com.github.asuslennikov.taskman.task.card

import com.github.asuslennikov.mvvm.presentation.AbstractViewModel
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class TaskCardViewModel @Inject constructor() : AbstractViewModel<TaskCardState>() {
    override fun buildInitialState(): TaskCardState = throw RuntimeException("Never build a state out of scratch. Id must be passed from outside")

    override fun mergeState(currentState: TaskCardState?, savedState: TaskCardState?): TaskCardState {
        return savedState?.let {
            TaskCardState(it.id, "Complete the test project", "", ZonedDateTime.now())
        } ?: throw RuntimeException("Initial state with id should be provided for this viewModel")
    }

    fun onEditTaskClick() {

    }
}