package com.github.asuslennikov.taskman.task.card

import androidx.navigation.fragment.navArgs
import com.github.asuslennikov.taskman.Fragment
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.databinding.TaskCardBinding

class TaskCardScreen : Fragment<TaskCardState, TaskCardViewModel, TaskCardBinding>(R.layout.task_card, TaskCardViewModel::class.java) {
    private val screenArgs: TaskCardScreenArgs by navArgs()

    override fun getSavedState(): TaskCardState? =
        super.getSavedState() ?: TaskCardState.loading(screenArgs.taskId)

}