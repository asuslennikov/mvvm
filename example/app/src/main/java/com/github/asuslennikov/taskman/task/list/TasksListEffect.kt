package com.github.asuslennikov.taskman.task.list

import com.github.asuslennikov.mvvm.api.presentation.Effect

sealed class TasksListEffect : Effect

class OpenAddTaskScreenEffect : TasksListEffect()