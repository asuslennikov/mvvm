package com.github.asuslennikov.taskman.task.card

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.asuslennikov.mvvm.api.presentation.Effect
import com.github.asuslennikov.taskman.Fragment
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.databinding.TaskCardBinding

class TaskCardScreen : Fragment<TaskCardState, TaskCardViewModel, TaskCardBinding>(R.layout.task_card, TaskCardViewModel::class.java) {
    private val screenArgs: TaskCardScreenArgs by navArgs()

    override fun getSavedState(): TaskCardState? =
        super.getSavedState() ?: TaskCardState.loading(screenArgs.taskId)

    override fun applyEffect(screenEffect: Effect) {
        super.applyEffect(screenEffect)
        when (screenEffect) {
            is OpenTaskEditScreenEffect -> findNavController().navigate(TaskCardScreenDirections.actionTaskCardToEditTask(screenEffect.taskId))
        }
    }
}