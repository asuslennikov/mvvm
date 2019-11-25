package com.github.asuslennikov.taskman.data

import android.content.Context
import com.github.asuslennikov.taskman.data.di.DataScope
import com.github.asuslennikov.taskman.domain.TaskRepository
import io.reactivex.Single
import javax.inject.Inject

@DataScope
class TaskRepositoryImpl @Inject constructor(private val context: Context) : TaskRepository {
    override fun getTaskById(id: Long): Single<Long> {
        return Single.just(id)
    }

}