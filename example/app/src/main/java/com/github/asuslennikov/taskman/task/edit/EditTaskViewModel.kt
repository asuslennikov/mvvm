package com.github.asuslennikov.taskman.task.edit

import android.content.Context
import com.github.asuslennikov.mvvm.api.domain.UseCaseOutput
import com.github.asuslennikov.mvvm.presentation.AbstractViewModel
import com.github.asuslennikov.taskman.domain.Task
import com.github.asuslennikov.taskman.domain.task.*
import com.github.asuslennikov.taskman.task.TaskDateMapper
import org.threeten.bp.Clock
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class EditTaskViewModel @Inject constructor(
    context: Context,
    private val clock: Clock,
    private val getByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : AbstractViewModel<EditTaskState>() {

    private val dateMapper = TaskDateMapper(context, clock)

    override fun buildInitialState(): EditTaskState = throw RuntimeException("Never build a state out of scratch. Id must be passed from outside")

    private fun loadingState(taskId: Long) = EditTaskState.loading(taskId)

    override fun mergeState(currentState: EditTaskState?, savedState: EditTaskState?): EditTaskState =
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
                                sendState(EditTaskState.error(taskId))
                            }
                        }
                        UseCaseOutput.Status.FAILURE -> sendState(EditTaskState.error(taskId))
                    }
                },
                { error ->
                    throw RuntimeException("Can't get task by id", error)
                }
            ))
    }

    private fun onTaskLoaded(task: Task): EditTaskState =
        task.run {
            EditTaskState.result(taskId, title, description, date, dateMapper.mapTaskDateToLabel(date))
        }

    fun onSaveTaskClick() {
        currentState.apply {
            updateTaskUseCase.execute(
                UpdateTaskInput(
                    Task(
                        id,
                        title,
                        description,
                        date,
                        false
                    )
                )
            ).subscribe()
        }
        sendEffect(CloseEditTaskScreenEffect())
    }

    fun onDateClicked() {
        sendEffect(OpenDateChooserEffect())
    }

    fun onDateSelected(year: Int, month: Int, dayOfMonth: Int) {
        val date =
            ZonedDateTime.now(clock).withYear(year).withMonth(month + 1).withDayOfMonth(dayOfMonth)
        sendState(
            currentState.copy(
                date = date,
                formattedDate = dateMapper.mapTaskDateToLabel(date)
            )
        )
    }

    fun onTitleChanged(title: String) {
        sendState(currentState.copy(title = title))
    }

    fun onDescriptionChanged(description: String) {
        sendState(currentState.copy(description = description))
    }
}