package com.github.asuslennikov.taskman.task.list.recycler

import androidx.recyclerview.widget.DiffUtil

internal class StateListItemCallback<T : ListItemState> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}