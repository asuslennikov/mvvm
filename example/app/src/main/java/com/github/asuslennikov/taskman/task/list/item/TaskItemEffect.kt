package com.github.asuslennikov.taskman.task.list.item

import com.github.asuslennikov.mvvm.api.presentation.Effect

sealed class TaskItemEffect : Effect

class OpenTaskCardEffect(val taskId: Long) : TaskItemEffect()