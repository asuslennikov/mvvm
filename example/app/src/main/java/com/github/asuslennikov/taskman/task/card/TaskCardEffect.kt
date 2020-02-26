package com.github.asuslennikov.taskman.task.card

import com.github.asuslennikov.mvvm.api.presentation.Effect

sealed class TaskCardEffect : Effect

class OpenTaskEditScreenEffect(val taskId: Long) : TaskCardEffect()