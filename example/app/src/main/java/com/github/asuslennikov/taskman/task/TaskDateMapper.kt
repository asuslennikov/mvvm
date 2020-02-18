package com.github.asuslennikov.taskman.task

import android.content.Context
import com.github.asuslennikov.taskman.R
import org.threeten.bp.Clock
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class TaskDateMapper(context: Context, private val clock: Clock) {
    private val todayListHeader = context.getString(R.string.tasks_list_today_header)
    private val yesterdayListHeader = context.getString(R.string.tasks_list_yesterday_header)
    private val anyDayListHeaderFormatter = DateTimeFormatter.ofPattern(context.getString(R.string.tasks_list_any_day_header_format))

    private lateinit var today: ZonedDateTime
    private lateinit var yesterday: ZonedDateTime

    init {
        refreshTodayReference()
    }

    fun refreshTodayReference() {
        today = ZonedDateTime.now(clock).withHour(0).withMinute(0).withSecond(0).withNano(0)
        yesterday = today.minusDays(1)
    }

    fun mapTaskDateToLabel(date: ZonedDateTime): String =
        when {
            date.isAfter(today) -> todayListHeader
            date.isAfter(yesterday) -> yesterdayListHeader
            else -> date.format(anyDayListHeaderFormatter)
        }
}