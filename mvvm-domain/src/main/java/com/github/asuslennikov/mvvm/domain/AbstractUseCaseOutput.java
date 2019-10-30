/**
 * Copyright 2019 Suslennikov Anton
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.asuslennikov.mvvm.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.asuslennikov.mvvm.api.domain.UseCase;
import com.github.asuslennikov.mvvm.api.domain.UseCaseOutput;

/**
 * It is base implementation for use case's output (see {@link UseCase}) and it allows to
 * inform about execution status (see the Loading/Content/Error pattern), also it can store
 * exception which was thrown during execution.
 */
public abstract class AbstractUseCaseOutput implements UseCaseOutput {
    private Throwable exception;
    private Status status = Status.SUCCESS;

    /**
     * Setter for exception which was thrown during execution.
     *
     * @param exception exact exception which was thrown during execution. Can be {@code null}.
     */
    public void setException(@Nullable Throwable exception) {
        this.exception = exception;
    }

    /**
     * This methods checks for saved exception (see {@link #setException(Throwable)}).
     *
     * @return {@code true} if the instance has saved exception
     */
    public boolean hasException() {
        return exception != null;
    }

    /**
     * This methods returns the saved exception (see {@link #setException(Throwable)}).
     *
     * @return the saved exception or {@code null}
     */
    @Nullable
    public Throwable getException() {
        return exception;
    }

    /**
     * Setter for current execution status of use case.
     *
     * @param status current status (can't be {@code null})
     */
    protected void setStatus(@NonNull Status status) {
        this.status = status;
    }

    /**
     * Getter for current execution status of use case.
     *
     * @return current status
     */
    @NonNull
    public Status getStatus() {
        return status;
    }
}
