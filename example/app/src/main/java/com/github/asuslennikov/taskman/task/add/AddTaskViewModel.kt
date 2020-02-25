package com.github.asuslennikov.taskman.task.add

import android.content.Context
import com.github.asuslennikov.mvvm.presentation.AbstractViewModel
import com.github.asuslennikov.taskman.task.TaskDateMapper
import org.threeten.bp.Clock
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class AddTaskViewModel @Inject constructor(
    private val clock: Clock,
    context: Context
) : AbstractViewModel<AddTaskState>() {

    private val dateMapper = TaskDateMapper(context, clock)

    override fun buildInitialState(): AddTaskState {
        val date = ZonedDateTime.now(clock)
        return AddTaskState("", "", date, dateMapper.mapTaskDateToLabel(date))
    }

    fun onSaveTaskClick() {
        sendEffect(CloseAddTaskScreenEffect())
    }

    fun onDateClicked() {
        sendEffect(OpenDateChooserEffect())
    }

    fun onDateSelected(year: Int, month : Int, dayOfMonth: Int){
        val date = ZonedDateTime.now(clock).withYear(year).withMonth(month + 1).withDayOfMonth(dayOfMonth)
        sendState(currentState.copy(date = date, formattedDate = dateMapper.mapTaskDateToLabel(date)))
    }
}