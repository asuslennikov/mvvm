package com.github.asuslennikov.taskman.task.add

import com.github.asuslennikov.mvvm.api.presentation.Effect

sealed class AddTaskEffect : Effect

class OpenDateChooserEffect : AddTaskEffect()

class CloseAddTaskScreenEffect : AddTaskEffect()