package com.github.asuslennikov.taskman.splash

import com.github.asuslennikov.mvvm.presentation.AbstractViewModel
import com.github.asuslennikov.taskman.TaskApplication

class SplashViewModel(private val app: TaskApplication) : AbstractViewModel<SplashState>() {
    override fun buildInitialState(): SplashState = SplashState(true)

}