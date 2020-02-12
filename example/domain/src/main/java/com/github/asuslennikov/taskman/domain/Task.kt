package com.github.asuslennikov.taskman.domain

import org.threeten.bp.ZonedDateTime

data class Task(
    var taskId: Long,
    val title: String,
    val description: String,
    val date: ZonedDateTime
)