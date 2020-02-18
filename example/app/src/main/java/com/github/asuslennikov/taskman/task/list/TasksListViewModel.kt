package com.github.asuslennikov.taskman.task.list

import android.content.Context
import com.github.asuslennikov.mvvm.api.domain.UseCaseOutput
import com.github.asuslennikov.mvvm.domain.EmptyUseCaseInput
import com.github.asuslennikov.mvvm.presentation.AbstractViewModel
import com.github.asuslennikov.taskman.domain.Task
import com.github.asuslennikov.taskman.domain.task.GetTasksUseCase
import com.github.asuslennikov.taskman.task.TaskDateMapper
import com.github.asuslennikov.taskman.task.list.item.DateHeaderState
import com.github.asuslennikov.taskman.task.list.item.TaskItemState
import com.github.asuslennikov.taskman.task.list.recycler.ListItemState
import org.threeten.bp.Clock
import javax.inject.Inject

class TasksListViewModel @Inject constructor(
    context: Context,
    clock: Clock,
    private val getTasksUseCase: GetTasksUseCase
) : AbstractViewModel<TasksListState>() {

    private val dateMapper = TaskDateMapper(context, clock)

    init {
        loadTasks()
    }

    override fun buildInitialState() = TasksListState(emptyList())

    private fun loadTasks() {
        collectDisposable(
            getTasksUseCase.execute(EmptyUseCaseInput.getInstance())
                .subscribe(
                    { output ->
                        when (output.status) {
                            UseCaseOutput.Status.IN_PROGRESS -> sendState(buildInitialState())
                            UseCaseOutput.Status.SUCCESS -> sendState(onTasksLoaded(output.tasks))
                            UseCaseOutput.Status.FAILURE -> sendState(buildInitialState())
                        }
                    },
                    { error ->
                        throw RuntimeException("Unexpected GetTasksUseCase execution error", error)
                    })
        )
    }

    private fun onTasksLoaded(tasks: List<Task>): TasksListState {
        val tasksPerDay = LinkedHashMap<String, MutableList<TaskItemState>>()
        tasks.forEach { task ->
            val taskDayLabel = dateMapper.mapTaskDateToLabel(task.date)
            tasksPerDay[taskDayLabel] = (tasksPerDay[taskDayLabel] ?: ArrayList()).apply {
                add(TaskItemState(task.taskId, task.title, task.completed))
            }
        }
        return TasksListState(ArrayList<ListItemState>().apply {
            tasksPerDay.entries.forEach { entry ->
                add(DateHeaderState(entry.key))
                addAll(entry.value)
            }
        })
    }

    fun onAddTaskClick() {

    }
}