package com.github.asuslennikov.taskman.domain

import io.reactivex.Observable

interface TaskRepository {
    fun getTasks(): Observable<List<Task>>
}