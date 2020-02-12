package com.github.asuslennikov.taskman.task.list.item

import com.github.asuslennikov.taskman.task.list.recycler.ListItemState

data class DateHeaderState(val date: String) : ListItemState {
    override fun getId() = date.hashCode().toLong()
}