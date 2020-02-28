package com.github.asuslennikov.taskman.task.add

import android.content.Context
import com.github.asuslennikov.mvvm.presentation.AbstractViewModel
import com.github.asuslennikov.taskman.domain.Task
import com.github.asuslennikov.taskman.domain.task.CreateTaskInput
import com.github.asuslennikov.taskman.domain.task.CreateTaskUseCase
import com.github.asuslennikov.taskman.task.TaskDateMapper
import org.threeten.bp.Clock
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(
    context: Context,
    private val clock: Clock,
    private val createTaskUseCase: CreateTaskUseCase
) : AbstractViewModel<AddTaskState>() {

    private val dateMapper = TaskDateMapper(context, clock)

    override fun buildInitialState(): AddTaskState {
        val date = ZonedDateTime.now(clock)
        return AddTaskState("", "", date, dateMapper.mapTaskDateToLabel(date))
    }

    fun onBackPressed(): Boolean {
        sendEffect(CloseAddTaskScreenEffect())
        return true
    }

    fun onSaveTaskClick() {
        currentState.apply {
            createTaskUseCase.execute(
                CreateTaskInput(
                    Task(
                        0,
                        title,
                        description,
                        date,
                        false
                    )
                )
            ).subscribe()
        }
        sendEffect(CloseAddTaskScreenEffect())
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