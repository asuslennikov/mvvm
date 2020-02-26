package com.github.asuslennikov.taskman.domain

import io.reactivex.Observable
import io.reactivex.Single

interface TaskManager {
    fun getTasks(): Observable<List<Task>>

    fun getTask(id: Long): Single<Task>

    fun createTask(task: Task): Single<Task>

    fun updateTask(task: Task): Single<Task>
}