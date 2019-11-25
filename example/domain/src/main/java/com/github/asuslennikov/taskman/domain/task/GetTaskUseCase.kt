package com.github.asuslennikov.taskman.domain.task

import com.github.asuslennikov.mvvm.api.domain.UseCase
import com.github.asuslennikov.taskman.domain.TaskRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) :
    UseCase<GetTaskInput, GetTaskOutput> {

    override fun execute(useCaseInput: GetTaskInput): Observable<GetTaskOutput> {
        return Observable.error(RuntimeException("Not implemented"))
    }
}
