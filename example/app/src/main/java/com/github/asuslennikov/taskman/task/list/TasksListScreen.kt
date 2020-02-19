package com.github.asuslennikov.taskman.task.list

import androidx.navigation.fragment.findNavController
import com.github.asuslennikov.mvvm.api.presentation.Effect
import com.github.asuslennikov.taskman.Fragment
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.databinding.TasksListBinding

class TasksListScreen : Fragment<TasksListState, TasksListViewModel, TasksListBinding>(
    R.layout.tasks_list,
    TasksListViewModel::class.java
) {

    override fun onStart() {
        super.onStart()
        binding.tasksListRecycler.adapter =
            TasksListAdapter(getViewModelProvider().linkWithStore(this))
    }

    override fun render(screenState: TasksListState) {
        super.render(screenState)
        (binding.tasksListRecycler.adapter as TasksListAdapter).submitList(screenState.tasks)
    }

    override fun applyEffect(screenEffect: Effect) {
        super.applyEffect(screenEffect)
        when (screenEffect) {
            is OpenAddTaskScreenEffect -> findNavController().navigate(R.id.action_tasksList_to_addTask)
        }
    }
}