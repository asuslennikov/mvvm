package com.github.asuslennikov.taskman.task.list.item

import com.github.asuslennikov.taskman.task.list.recycler.ListItemState

data class TaskItemState(
    private val id: Long,
    val title: String,
    val completed: Boolean
) : ListItemState {
    override fun getId() = id
}