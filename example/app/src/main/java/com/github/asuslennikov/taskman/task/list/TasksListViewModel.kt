package com.github.asuslennikov.taskman.task.list

import android.content.Context
import com.github.asuslennikov.mvvm.api.domain.UseCaseOutput
import com.github.asuslennikov.mvvm.domain.EmptyUseCaseInput
import com.github.asuslennikov.mvvm.presentation.AbstractViewModel
import com.github.asuslennikov.taskman.R
import com.github.asuslennikov.taskman.domain.Task
import com.github.asuslennikov.taskman.domain.task.GetTasksUseCase
import com.github.asuslennikov.taskman.task.list.item.DateHeaderState
import com.github.asuslennikov.taskman.task.list.item.TaskItemState
import com.github.asuslennikov.taskman.task.list.recycler.ListItemState
import org.threeten.bp.Clock
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class TasksListViewModel @Inject constructor(
    private val clock: Clock,
    private val context: Context,
    private val getTasksUseCase: GetTasksUseCase
) : AbstractViewModel<TasksListState>() {

    private val todayListHeader = context.getString(R.string.tasks_list_today_header)
    private val yesterdayListHeader = context.getString(R.string.tasks_list_yesterday_header)
    private val anyDayListHeaderFormatter = DateTimeFormatter.ofPattern(context.getString(R.string.tasks_list_any_day_header_format))

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
        val today = ZonedDateTime.now(clock).withHour(0).withMinute(0).withSecond(0).withNano(0)
        val yesterday = today.minusDays(1)
        val tasksPerDay = LinkedHashMap<String, MutableList<TaskItemState>>()
        tasks.forEach { task ->
            val taskDayLabel = when {
                task.date.isAfter(today) -> todayListHeader
                task.date.isAfter(yesterday) -> yesterdayListHeader
                else -> task.date.format(anyDayListHeaderFormatter)
            }
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