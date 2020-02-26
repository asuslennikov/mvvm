package com.github.asuslennikov.taskman.domain.task

import com.github.asuslennikov.mvvm.domain.AbstractUseCase
import com.github.asuslennikov.mvvm.domain.EmptyUseCaseInput
import com.github.asuslennikov.mvvm.domain.UseCaseExecution
import com.github.asuslennikov.taskman.domain.TaskManager
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskManager: TaskManager
) : AbstractUseCase<EmptyUseCaseInput, GetTasksOutput>() {

    override fun getUseCaseOutput(useCaseInput: EmptyUseCaseInput) = GetTasksOutput(emptyList())

    override fun doExecute(
        useCaseInput: EmptyUseCaseInput,
        execution: UseCaseExecution<EmptyUseCaseInput, GetTasksOutput>
    ) {
        execution.notifyProgress()
        execution.joinTask("getTasks", taskManager.getTasks()
            .subscribe(
                { tasks ->
                    execution.notifySuccess(GetTasksOutput(tasks))
                },
                { error ->
                    execution.notifyFailure(error)
                }
            )
        )
    }
}
