package com.github.asuslennikov.taskman.domain

import org.threeten.bp.ZonedDateTime

data class Task(
    val taskId: Long,
    val title: String,
    val description: String,
    val date: ZonedDateTime,
    val completed: Boolean
)