package com.github.asuslennikov.taskman.task.list

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.END
import androidx.recyclerview.widget.ItemTouchHelper.START
import androidx.recyclerview.widget.RecyclerView
import com.github.asuslennikov.mvvm.api.presentation.Effect
import com.github.asuslennikov.taskman.Fragment
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.databinding.TasksListBinding
import com.github.asuslennikov.taskman.task.list.item.TaskItemScreen

class TasksListScreen : Fragment<TasksListState, TasksListViewModel, TasksListBinding>(
    R.layout.tasks_list,
    TasksListViewModel::class.java
) {

    override fun onStart() {
        super.onStart()
        binding.tasksListRecycler.adapter =
            TasksListAdapter(getViewModelProvider().linkWithStore(this))
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, START or END) {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) =
                if (viewHolder is TaskItemScreen) super.getMovementFlags(recyclerView, viewHolder) else 0

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as? TaskItemScreen)?.onItemSwiped()
            }
        }).attachToRecyclerView(binding.tasksListRecycler)
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