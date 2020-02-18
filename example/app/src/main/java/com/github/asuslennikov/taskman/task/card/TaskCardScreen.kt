package com.github.asuslennikov.taskman.task.card

import android.text.TextUtils
import android.view.View
import androidx.navigation.fragment.navArgs
import com.github.asuslennikov.taskman.Fragment
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.databinding.TaskCardBinding

class TaskCardScreen : Fragment<TaskCardState, TaskCardViewModel, TaskCardBinding>(R.layout.task_card, TaskCardViewModel::class.java) {
    private val screenArgs: TaskCardScreenArgs by navArgs()

    override fun getSavedState(): TaskCardState? =
        super.getSavedState() ?: TaskCardState(screenArgs.taskId, "", "", "")

    override fun render(screenState: TaskCardState) {
        super.render(screenState)
        if (TextUtils.isEmpty(screenState.description)) {
            binding.taskCardDescription.visibility = View.GONE
        } else {
            binding.taskCardDescription.visibility = View.VISIBLE
        }
    }
}