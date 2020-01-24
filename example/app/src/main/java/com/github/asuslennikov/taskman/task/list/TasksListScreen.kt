package com.github.asuslennikov.taskman.task.list

import com.github.asuslennikov.taskman.Fragment
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.databinding.TasksListBinding

class TasksListScreen : Fragment<TasksListState, TasksListViewModel, TasksListBinding>(
    R.layout.tasks_list,
    TasksListViewModel::class.java
) {

}