package com.github.asuslennikov.taskman.task.card

import android.content.Context
import com.github.asuslennikov.mvvm.api.domain.UseCaseOutput
import com.github.asuslennikov.mvvm.presentation.AbstractViewModel
import com.github.asuslennikov.taskman.domain.Task
import com.github.asuslennikov.taskman.domain.task.GetTaskByIdInput
import com.github.asuslennikov.taskman.domain.task.GetTaskByIdUseCase
import com.github.asuslennikov.taskman.task.TaskDateMapper
import org.threeten.bp.Clock
import javax.inject.Inject

class TaskCardViewModel @Inject constructor(
    context: Context,
    clock: Clock,
    private val getByIdUseCase: GetTaskByIdUseCase
) : AbstractViewModel<TaskCardState>() {

    private val dateMapper = TaskDateMapper(context, clock)

    override fun buildInitialState(): TaskCardState = throw RuntimeException("Never build a state out of scratch. Id must be passed from outside")

    private fun loadingState(taskId: Long) = TaskCardState.loading(taskId)

    override fun mergeState(currentState: TaskCardState?, savedState: TaskCardState?): TaskCardState =
        savedState?.let {
            loadTask(it.id)
            loadingState(it.id)
        } ?: throw RuntimeException("Initial state with id should be provided for this viewModel")


    private fun loadTask(taskId: Long) {
        collectDisposable(getByIdUseCase.execute(GetTaskByIdInput(taskId))
            .subscribe(
                { result ->
                    when (result.status) {
                        UseCaseOutput.Status.IN_PROGRESS -> sendState(loadingState(taskId))
                        UseCaseOutput.Status.SUCCESS -> {
                            if (result.found) {
                                result.task?.also { sendState(onTaskLoaded(it)) }
                            } else {

                            }
                        }
                        UseCaseOutput.Status.FAILURE -> sendState(loadingState(taskId))
                    }
                },
                { error ->
                    throw RuntimeException("Can't get task by id", error)
                }
            ))
    }

    private fun onTaskLoaded(task: Task): TaskCardState =
        task.run {
            TaskCardState.result(taskId, title, description, dateMapper.mapTaskDateToLabel(date))
        }

    fun onEditTaskClick() {

    }
}