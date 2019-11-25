package com.github.asuslennikov.taskman

import com.github.asuslennikov.mvvm.presentation.ViewModelProvider

interface ComponentRegistry {
    fun getViewModelProvider(): ViewModelProvider
}