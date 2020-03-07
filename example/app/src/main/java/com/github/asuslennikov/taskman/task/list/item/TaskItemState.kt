package com.github.asuslennikov.taskman.task.list.item

import com.github.asuslennikov.taskman.task.list.recycler.ListItemState

data class TaskItemState(
    private val id: Long,
    val title: String,
    val completed: Boolean
) : ListItemState {
    override fun getId() = id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TaskItemState

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}