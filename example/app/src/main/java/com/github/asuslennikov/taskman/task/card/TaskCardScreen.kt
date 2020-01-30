package com.github.asuslennikov.taskman.task.card

import androidx.navigation.fragment.navArgs
import com.github.asuslennikov.taskman.Fragment
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.databinding.TaskCardBinding
import org.threeten.bp.Instant

class TaskCardScreen : Fragment<TaskCardState, TaskCardViewModel, TaskCardBinding>(R.layout.task_card, TaskCardViewModel::class.java) {
    private val screenArgs: TaskCardScreenArgs by navArgs()

    override fun getSavedState(): TaskCardState? {
        return super.getSavedState() ?: TaskCardState(screenArgs.taskId, "", "", Instant.EPOCH)
    }
}