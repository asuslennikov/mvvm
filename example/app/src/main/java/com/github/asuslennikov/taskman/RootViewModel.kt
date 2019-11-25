package com.github.asuslennikov.taskman

import com.github.asuslennikov.mvvm.presentation.AbstractViewModel
import com.github.asuslennikov.taskman.domain.task.GetTaskUseCase
import javax.inject.Inject

class RootViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskUseCase
) : AbstractViewModel<RootState>() {

    override fun buildInitialState(): RootState {
        return RootState("Hello world")
    }
}
