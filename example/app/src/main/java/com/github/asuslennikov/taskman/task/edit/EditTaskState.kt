package com.github.asuslennikov.taskman.task.edit

import com.github.asuslennikov.mvvm.api.presentation.State
import org.threeten.bp.ZonedDateTime

data class EditTaskState private constructor(
    val loading: Boolean,
    val failed: Boolean,
    val id: Long,
    val title: String,
    val description: String,
    val date: ZonedDateTime,
    val formattedDate: String
) : State {

    companion object {
        fun loading(taskId: Long) =
            EditTaskState(loading = true, failed = false, id = taskId, title = "", description = "", date = ZonedDateTime.now(), formattedDate = "")

        fun error(taskId: Long) =
            EditTaskState(loading = false, failed = true, id = taskId, title = "", description = "", date = ZonedDateTime.now(), formattedDate = "")

        fun result(taskId: Long, title: String, description: String, date: ZonedDateTime, formattedDate: String) =
            EditTaskState(
                loading = false,
                failed = false,
                id = taskId,
                title = title,
                description = description,
                date = date,
                formattedDate = formattedDate
            )
    }

    fun isCompleted() = !loading && !failed
}