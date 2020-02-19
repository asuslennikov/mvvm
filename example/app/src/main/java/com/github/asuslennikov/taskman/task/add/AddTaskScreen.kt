package com.github.asuslennikov.taskman.task.add

import com.github.asuslennikov.taskman.Fragment
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.databinding.AddTaskBinding

class AddTaskScreen : Fragment<AddTaskState, AddTaskViewModel, AddTaskBinding>(
    R.layout.add_task,
    AddTaskViewModel::class.java
) {

}