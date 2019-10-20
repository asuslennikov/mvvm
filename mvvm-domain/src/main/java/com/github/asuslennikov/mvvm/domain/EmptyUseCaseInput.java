package com.github.asuslennikov.mvvm.domain;

import com.github.asuslennikov.mvvm.api.domain.UseCaseInput;

public final class EmptyUseCaseInput implements UseCaseInput {
    private static volatile EmptyUseCaseInput instance;

    private EmptyUseCaseInput() {
        // private constructor
    }

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
