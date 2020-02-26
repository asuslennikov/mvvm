package com.github.asuslennikov.taskman.task.edit

import com.github.asuslennikov.mvvm.api.presentation.Effect

sealed class EditTaskEffect : Effect

class OpenDateChooserEffect : EditTaskEffect()

class CloseEditTaskScreenEffect : EditTaskEffect()