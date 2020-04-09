package com.github.asuslennikov.taskman.task.list.item

import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import com.github.asuslennikov.mvvm.api.presentation.Effect
import com.github.asuslennikov.mvvm.presentation.BR
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.databinding.TaskItemBinding
import com.github.asuslennikov.taskman.task.list.TasksListScreenDirections
import com.github.asuslennikov.taskman.task.list.recycler.BoundViewHolder

class TaskItemScreen(itemView: View, viewModel: TaskItemViewModel) :
    BoundViewHolder<TaskItemState, TaskItemViewModel, TaskItemBinding>(itemView, viewModel) {

    override fun render(screenState: TaskItemState) {
        super.render(screenState)
        if (screenState.completed) {
            binding.taskItemRoot.background = ContextCompat.getDrawable(itemView.context, R.drawable.tasks_list_task_item_completed_background)
            binding.taskItemTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.task_item_completed_text))
            binding.taskItemTitle.paintFlags = binding.taskItemTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            binding.taskItemRoot.background = ContextCompat.getDrawable(itemView.context, R.drawable.tasks_list_task_item_background)
            binding.taskItemTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.task_item_text))
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

    fun onItemSwiped() {
        viewModel.onTaskSwiped(this)
    }
}