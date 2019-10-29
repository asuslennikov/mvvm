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

import com.github.asuslennikov.mvvm.api.domain.UseCaseInput;

/**
 * If some use case doesn't need an input, they can define this type as an input type.
 */
public final class EmptyUseCaseInput implements UseCaseInput {
    private static volatile EmptyUseCaseInput instance;

    private EmptyUseCaseInput() {
        // private constructor
    }

    /**
     * It provides the instance of use case input which has no fields and it means that use case
     * actually doesn't need an input.
     *
     * @return stub for use case input.
     */
    public static EmptyUseCaseInput getInstance() {
        EmptyUseCaseInput localInstance = instance;
        if (localInstance == null) {
            synchronized (EmptyUseCaseInput.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new EmptyUseCaseInput();
                }
            }
        }
        return localInstance;
    }
}
