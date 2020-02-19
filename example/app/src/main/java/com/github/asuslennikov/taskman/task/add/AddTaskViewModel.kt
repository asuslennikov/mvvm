package com.github.asuslennikov.taskman.task.add

import com.github.asuslennikov.mvvm.presentation.AbstractViewModel
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(

) : AbstractViewModel<AddTaskState>() {

    override fun buildInitialState(): AddTaskState =
        AddTaskState("", "", "")
}