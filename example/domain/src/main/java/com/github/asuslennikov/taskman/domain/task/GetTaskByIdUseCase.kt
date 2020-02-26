package com.github.asuslennikov.taskman.domain.task

import com.github.asuslennikov.mvvm.domain.AbstractUseCase
import com.github.asuslennikov.mvvm.domain.UseCaseExecution
import com.github.asuslennikov.taskman.domain.TaskManager
import com.github.asuslennikov.taskman.domain.di.DomainScope
import javax.inject.Inject

@DomainScope
class GetTaskByIdUseCase @Inject constructor(
    private val taskManager: TaskManager
) : AbstractUseCase<GetTaskByIdInput, GetTaskByIdOutput>() {

    override fun getUseCaseOutput(useCaseInput: GetTaskByIdInput): GetTaskByIdOutput =
        GetTaskByIdOutput(null, false)

    override fun doExecute(
        useCaseInput: GetTaskByIdInput,
        execution: UseCaseExecution<GetTaskByIdInput, GetTaskByIdOutput>
    ) {
        execution.notifyProgress()
        execution.joinTask(
            "getTaskById", taskManager.getTask(useCaseInput.taskId)
                .subscribe(
                    { task ->
                        execution.notifySuccess(GetTaskByIdOutput(task, true))
                    },
                    { error ->
                        execution.notifyFailure(GetTaskByIdOutput(null, false).apply {
                            exception = error
                        })
                }
            ))
    }
}