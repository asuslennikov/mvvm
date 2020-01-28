package com.github.asuslennikov.taskman.task.list

import android.view.ViewGroup
import com.github.asuslennikov.mvvm.presentation.ViewModelProvider
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.task.list.item.*
import com.github.asuslennikov.taskman.task.list.recycler.AbstractRecyclerViewAdapter
import com.github.asuslennikov.taskman.task.list.recycler.BoundViewHolder
import com.github.asuslennikov.taskman.task.list.recycler.ListItemState

class TasksListAdapter(viewModelProvider: ViewModelProvider.Linked) : AbstractRecyclerViewAdapter(viewModelProvider) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoundViewHolder<out ListItemState, *> {
        return when (viewType) {
            DateHeaderState::class.java.hashCode() -> DateHeaderScreen(
                inflate(parent, R.layout.date_header),
                createViewModel(DateHeaderViewModel::class.java)
            )
            TaskItemState::class.java.hashCode() -> TaskItemScreen(
                inflate(parent, R.layout.task_item),
                createViewModel(TaskItemViewModel::class.java)
            )
            else -> throw RuntimeException("Unknown view type for tasks list")
        }
    }
}