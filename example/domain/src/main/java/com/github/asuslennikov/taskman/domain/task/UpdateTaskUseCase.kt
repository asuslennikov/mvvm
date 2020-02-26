package com.github.asuslennikov.taskman.domain.task

import com.github.asuslennikov.mvvm.domain.AbstractUseCase
import com.github.asuslennikov.mvvm.domain.UseCaseExecution
import com.github.asuslennikov.taskman.domain.TaskManager
import com.github.asuslennikov.taskman.domain.di.DomainScope
import javax.inject.Inject

@DomainScope
class UpdateTaskUseCase @Inject constructor(
    private val taskManager: TaskManager
) : AbstractUseCase<UpdateTaskInput, UpdateTaskOutput>() {

    override fun getUseCaseOutput(input: UpdateTaskInput): UpdateTaskOutput =
        UpdateTaskOutput(input.task)

    override fun doExecute(
        useCaseInput: UpdateTaskInput,
        execution: UseCaseExecution<UpdateTaskInput, UpdateTaskOutput>
    ) {
        execution.notifyProgress()
        execution.joinTask("updateTask", taskManager.updateTask(useCaseInput.task)
            .subscribe(
                { task ->
                    execution.notifySuccess(UpdateTaskOutput(task))
                },
                { error ->
                    execution.notifyFailure(UpdateTaskOutput(useCaseInput.task).apply {
                        exception = error
                    })
                }
            ))
    }

}