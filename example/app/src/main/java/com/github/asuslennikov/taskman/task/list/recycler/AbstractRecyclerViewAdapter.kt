package com.github.asuslennikov.taskman.task.list.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.github.asuslennikov.mvvm.api.presentation.State
import com.github.asuslennikov.mvvm.api.presentation.ViewModel
import com.github.asuslennikov.mvvm.presentation.ViewModelProvider

abstract class AbstractRecyclerViewAdapter(private val viewModelProvider: ViewModelProvider.Linked) :
    ListAdapter<ListItemState, BoundViewHolder<out ListItemState, *>>(StateListItemCallback()) {

    protected fun inflate(parent: ViewGroup, layoutResourceId: Int): View =
        LayoutInflater.from(parent.context).inflate(layoutResourceId, parent, false)

    protected fun <STATE : State, VM : ViewModel<STATE>> createViewModel(viewModelClass: Class<VM>): VM =
        viewModelProvider.getViewModel(viewModelClass)

    override fun getItemViewType(position: Int): Int = getItem(position).run { this::class.java.hashCode() }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BoundViewHolder<out ListItemState, *>, position: Int) {
        (holder as BoundViewHolder<ListItemState, *>).bind(getItem(position))
    }
}