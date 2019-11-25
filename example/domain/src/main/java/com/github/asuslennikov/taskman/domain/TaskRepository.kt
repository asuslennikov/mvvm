package com.github.asuslennikov.taskman.domain

import io.reactivex.Single

interface TaskRepository {
    fun getTaskById(id: Long): Single<Long>
}