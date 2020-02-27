package com.github.asuslennikov.taskman.domain.task

import com.github.asuslennikov.mvvm.domain.AbstractUseCase
import com.github.asuslennikov.mvvm.domain.UseCaseExecution
import com.github.asuslennikov.taskman.domain.TaskManager
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskManager: TaskManager
) : AbstractUseCase<DeleteTaskInput, DeleteTaskOutput>() {

    override fun getUseCaseOutput(useCaseInput: DeleteTaskInput) =
        DeleteTaskOutput()

    override fun doExecute(useCaseInput: DeleteTaskInput, execution: UseCaseExecution<DeleteTaskInput, DeleteTaskOutput>) {
        execution.notifyProgress()
        execution.joinTask("deleteTask", taskManager.deleteTask(useCaseInput.taskId).subscribe(
            { execution.notifySuccess() },
            { error ->
                execution.notifyFailure(DeleteTaskOutput().apply {
                    exception = error
                })
            }
        ))
    }
}