package com.github.asuslennikov.taskman.data

import com.github.asuslennikov.taskman.data.database.TaskDao
import com.github.asuslennikov.taskman.data.di.DataScope
import com.github.asuslennikov.taskman.domain.Task
import com.github.asuslennikov.taskman.domain.TaskRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

@DataScope
class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    private val taskMapper = TaskEntityMapper()

    override fun getTasks(): Observable<List<Task>> =
        taskDao.getTasks()
            .map { tasks ->
                tasks.map { entity -> taskMapper.fromEntity(entity) }
            }

    override fun getTask(id: Long): Single<Task> =
        taskDao.getById(id)?.let {
            Single.just(taskMapper.fromEntity(it))
        } ?: Single.error(RuntimeException("Task with id=$id was not found"))
}