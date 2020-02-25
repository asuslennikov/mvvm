package com.github.asuslennikov.taskman.task.add

import com.github.asuslennikov.mvvm.api.presentation.State
import org.threeten.bp.ZonedDateTime

data class AddTaskState(
    val title: String,
    val description: String,
    val date: ZonedDateTime,
    val formattedDate: String
): State