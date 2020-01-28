package com.github.asuslennikov.taskman.task.list

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
}