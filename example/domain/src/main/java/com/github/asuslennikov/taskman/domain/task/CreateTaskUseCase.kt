package com.github.asuslennikov.taskman.domain.task

import com.github.asuslennikov.mvvm.domain.AbstractUseCase
import com.github.asuslennikov.mvvm.domain.UseCaseExecution
import com.github.asuslennikov.taskman.domain.TaskManager
import com.github.asuslennikov.taskman.domain.di.DomainScope
import javax.inject.Inject

@DomainScope
class CreateTaskUseCase @Inject constructor(
    private val taskManager: TaskManager
) : AbstractUseCase<CreateTaskInput, CreateTaskOutput>() {

    override fun getUseCaseOutput(input: CreateTaskInput): CreateTaskOutput =
        CreateTaskOutput(input.task)

    override fun doExecute(
        useCaseInput: CreateTaskInput,
        execution: UseCaseExecution<CreateTaskInput, CreateTaskOutput>
    ) {
        execution.notifyProgress()
        execution.joinTask("createTask", taskManager.createTask(useCaseInput.task)
            .subscribe(
                { task ->
                    execution.notifySuccess(CreateTaskOutput(task))
                },
                { error ->
                    execution.notifyFailure(CreateTaskOutput(useCaseInput.task).apply {
                        exception = error
                    })
                }
            ))
    }

}