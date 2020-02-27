package com.github.asuslennikov.taskman.data

import com.github.asuslennikov.taskman.data.database.TaskDao
import com.github.asuslennikov.taskman.data.di.DataScope
import com.github.asuslennikov.taskman.domain.Task
import com.github.asuslennikov.taskman.domain.TaskManager
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

@DataScope
class TaskManagerImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskManager {

    private val taskMapper = TaskEntityMapper()

    override fun getTasks(): Observable<List<Task>> =
        taskDao.getTasks()
            .map { tasks ->
                tasks.map { entity -> taskMapper.fromEntity(entity) }
            }

    override fun getTask(id: Long): Single<Task> =
        Single.just(Any())
            .flatMap {
                taskDao.getById(id)?.let {
                    Single.just(taskMapper.fromEntity(it))
                } ?: Single.error(RuntimeException("Task with id=$id was not found"))
            }

    override fun createTask(task: Task): Single<Task> =
        Single.fromCallable {
            val id = taskDao.insert(taskMapper.toEntity(task))
            taskDao.getById(id)
        }
            .map { entity -> taskMapper.fromEntity(entity) }

    override fun updateTask(task: Task): Single<Task> =
        Single.fromCallable {
            taskDao.update(taskMapper.toEntity(task))
            taskDao.getById(task.taskId)
        }
            .map { entity -> taskMapper.fromEntity(entity) }

    override fun deleteTask(id: Long) =
        Completable.fromCallable {
            taskDao.getById(id)?.let { task ->
                taskDao.delete(task)
            }
        }
}