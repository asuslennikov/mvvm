package com.github.asuslennikov.taskman.task.list.item

import android.graphics.Paint
import android.view.View
import androidx.navigation.findNavController
import com.github.asuslennikov.mvvm.api.presentation.Effect
import com.github.asuslennikov.mvvm.presentation.BR
import com.github.asuslennikov.taskman.databinding.TaskItemBinding
import com.github.asuslennikov.taskman.task.list.TasksListScreenDirections
import com.github.asuslennikov.taskman.task.list.recycler.BoundViewHolder

class TaskItemScreen(itemView: View, viewModel: TaskItemViewModel) :
    BoundViewHolder<TaskItemState, TaskItemViewModel, TaskItemBinding>(itemView, viewModel) {

    override fun render(screenState: TaskItemState) {
        super.render(screenState)
        if (screenState.completed) {
            binding.taskItemTitle.paintFlags = binding.taskItemTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            binding.taskItemTitle.paintFlags = binding.taskItemTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun getBindingScreenVariableId(): Int = BR.screen

    override fun screenSupportsEffects(): Boolean = true

    override fun applyEffect(screenEffect: Effect) {
        super.applyEffect(screenEffect)
        when (screenEffect) {
            is OpenTaskCardEffect -> {
                itemView.findNavController().navigate(TasksListScreenDirections.actionTasksListToTaskCard(screenEffect.taskId))
            }
        }
    }
}