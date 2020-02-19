package com.github.asuslennikov.taskman.task.card

import android.text.TextUtils
import com.github.asuslennikov.mvvm.api.presentation.State

data class TaskCardState private constructor(
    val loading: Boolean,
    val failed: Boolean,
    val id: Long,
    val title: String,
    val description: String,
    val date: String
) : State {

    companion object {
        fun loading(taskId: Long) = TaskCardState(loading = true, failed = false, id = taskId, title = "", description = "", date = "")

        fun error(taskId: Long) = TaskCardState(loading = false, failed = true, id = taskId, title = "", description = "", date = "")

        fun result(taskId: Long, title: String, description: String, date: String) =
            TaskCardState(loading = false, failed = false, id = taskId, title = title, description = description, date = date)
    }

    fun isCompleted() = !loading && !failed

    fun getDescriptionVisibility() = isCompleted() && !TextUtils.isEmpty(description)

    fun getDateVisibility() = isCompleted() && !TextUtils.isEmpty(date)
}