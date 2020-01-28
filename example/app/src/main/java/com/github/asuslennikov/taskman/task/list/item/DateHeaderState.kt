package com.github.asuslennikov.taskman.task.list.item

import com.github.asuslennikov.taskman.task.list.recycler.ListItemState

data class DateHeaderState(private val id: Long, val date: String) : ListItemState {
    override fun getId() = id
}